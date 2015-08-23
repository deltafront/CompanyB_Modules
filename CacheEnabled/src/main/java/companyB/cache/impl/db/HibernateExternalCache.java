package companyB.cache.impl.db;

import companyB.cache.AbstractExternalCache;
import companyB.cache.ExternalCache;
import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * Hibernate-based implementation of ExternalCache.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.2.0
 */
public class HibernateExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private Session session;
    private Transaction transaction;
    private final Configuration configuration;

    /**
     * Uses Hibernate file configuration values from hibernate.cgf.xml file on the classpath.
     * @param name Name of this cache.
     * @since 2.2.0
     */
    public HibernateExternalCache(String name)
    {
        this(name,null);
    }

    /**
     * Allows the configuration object to be passed in via the constructor.
     * @param name Name of this cache.
     * @param configuration Configurations to use.
     * @since 2.2.0
     */
    public HibernateExternalCache(String name, Configuration configuration)
    {
        super(name);
        this.configuration = configuration;
    }

    @Override
    public void insert(String key, String value)
    {
        Validate.notBlank(key,"Key must be provided!");
        value = normalizer.cleanNullStringValue(value);
        String existing = retrieve(key);
        startSessionTransaction();
        if(null != existing) doUpdate(key, value);
        else doSave(key, value);
        transaction.commit();
        session.close();
    }

    @Override
    public String retrieve(String key)
    {
        startSessionTransaction();
        String value = (String)session.createQuery("SELECT value FROM CacheEntry WHERE key = :key")
                .setString("key",key)
                .uniqueResult();
        value = normalizer.dirtyNullStringValue(value);
        LOGGER.trace(String.format("Returning value for key '%s' : '%s'.",key,value));
        session.close();
        return value;
    }

    @Override
    public void clear()
    {
        startSessionTransaction();
        Query query= session.createQuery("DELETE FROM CacheEntry");
        Integer cleared = query.executeUpdate();
        transaction.commit();
        session.close();
        LOGGER.trace(String.format("%d Cache Entries deleted.",cleared));
    }

    @Override
    public String remove(String key)
    {
        startSessionTransaction();
        String value = (String)session.createQuery("SELECT value FROM CacheEntry WHERE key = :key")
                .setString("key",key)
                .uniqueResult();
        value = normalizer.dirtyNullStringValue(value);
        if(null != value)doRemove(key, value);
        session.close();
        LOGGER.trace(String.format("Removed value for key '%s' ('%s')? %b",key,value,null != value));
        return value;
    }


    @Override
    public String getName()
    {
        return name;
    }
    private void doSave(String key, String value)
    {
        Long id = (Long)session.save(new CacheEntry(key,value));
        LOGGER.trace(String.format("ID for newly saved cache entry ('%s'=>'%s'): %d",key,value,id));
    }

    private void doUpdate(String key, String value)
    {
        Integer updated = session.createQuery("UPDATE CacheEntry SET value = :value WHERE key = :key")
                .setString("value", value)
                .setString("key", key)
                .executeUpdate();
        LOGGER.trace(String.format("%d items updated where '%s' = '%s'.",updated,key,value));
    }
    private void doRemove(String key, String value)
    {
        Integer removed = session.createQuery("DELETE FROM CacheEntry WHERE key = :key AND value = :value")
                .setString("key",key)
                .setString("value",value)
                .executeUpdate();
        LOGGER.trace(String.format("Number of items removed? %d",removed));
        transaction.commit();
    }
    private void startSessionTransaction()
    {
        Configuration configuration = getConfiguration();
        StandardServiceRegistryBuilder builder = getStandardServiceRegistryBuilder(configuration);
        SessionFactory sessionFactory = getSessionFactory(configuration, builder);
        openSession(sessionFactory);
        beginTransaction();
    }

    private void beginTransaction()
    {
        transaction = session.beginTransaction();
        Validate.notNull(transaction, "Transaction was not started. Aborting.");
        LOGGER.trace("Transaction started.");
    }

    private void openSession(SessionFactory sessionFactory)
    {
        session = sessionFactory.openSession();
        Validate.notNull(session, "Session was not returned. Aborting.");
        LOGGER.trace("New session started.");
    }

    private SessionFactory getSessionFactory(Configuration configuration, StandardServiceRegistryBuilder builder)
    {
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        Validate.notNull(sessionFactory, "Session Factory not created. Aborting.");
        LOGGER.trace("Session Factory created.");
        return sessionFactory;
    }

    private StandardServiceRegistryBuilder getStandardServiceRegistryBuilder(Configuration configuration)
    {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        Properties properties = configuration.getProperties();
        String props = getPropertiesString(properties);
        Validate.notNull(builder, "Null Standard Service Registry Builder returned. Aborting.");
        LOGGER.trace(String.format("Standard Registry Builder built with values from configuration:\n%s",props));
        return builder;
    }

    private String getPropertiesString(Properties properties)
    {
        String props = "";
        for(String key : properties.stringPropertyNames())
        {
            props += String.format("\t%s=>%s",key,properties.getProperty(key));
        }
        return props;
    }

    private Configuration getConfiguration()
    {
        Configuration configuration =
                (null == this.configuration) ?
                        new Configuration().configure() : this.configuration;
        Validate.notNull(configuration, "Null configuration returned. Aborting.");
        LOGGER.trace("Configuration instance configured.");
        return configuration;
    }
}
