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
 * @since 2.1.0
 */
public class HibernateExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private Session session;
    private Transaction transaction;
    private final Configuration configuration;

    /**
     * Uses Hibernate file configuration values from hibernate.cgf.xml file on the classpath.
     * @param name Name of this cache.
     * @since 2.1.0
     */
    public HibernateExternalCache(String name)
    {
        this(name,null);
    }

    /**
     * Allows the configuration object to be passed in via the constructor.
     * @param name Name of this cache.
     * @param configuration Configurations to use.
     * @since 2.1.0
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
        final String newvalue = normalizer.cleanNullStringValue(value);
        String existing = retrieve(key);
        startSessionTransaction();
        if(null != existing) doUpdate(key, newvalue);
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
        LOGGER.trace("Returning value for key '{}' : '{}'.",key,value);
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
        LOGGER.trace("{} Cache Entries deleted.",cleared);
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
        LOGGER.trace("Removed value for key '{}' ('{}')? {}",key,value,null != value);
        return value;
    }


    @Override
    public String getName()
    {
        return name;
    }
    private void doSave(String key, String value)
    {
        final Long id = (Long)session.save(new CacheEntry(key,value));
        LOGGER.trace("ID for newly saved cache entry ('{}'=>'{}'): {}",key,value,id);
    }

    private void doUpdate(String key, String value)
    {
        final Integer updated = session.createQuery("UPDATE CacheEntry SET value = :value WHERE key = :key")
                .setString("value", value)
                .setString("key", key)
                .executeUpdate();
        LOGGER.trace("{} items updated where '{}' = '{}'.",updated,key,value);
    }
    private void doRemove(String key, String value)
    {
        final Integer removed = session.createQuery("DELETE FROM CacheEntry WHERE key = :key AND value = :value")
                .setString("key",key)
                .setString("value",value)
                .executeUpdate();
        LOGGER.trace("Number of items removed? {}",removed);
        transaction.commit();
    }
    private void startSessionTransaction()
    {
        final Configuration configuration = getConfiguration();
        final StandardServiceRegistryBuilder builder = getStandardServiceRegistryBuilder(configuration);
        final SessionFactory sessionFactory = getSessionFactory(configuration, builder);
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
        final SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        Validate.notNull(sessionFactory, "Session Factory not created. Aborting.");
        LOGGER.trace("Session Factory created.");
        return sessionFactory;
    }

    private StandardServiceRegistryBuilder getStandardServiceRegistryBuilder(Configuration configuration)
    {
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        final Properties properties = configuration.getProperties();
        final String props = getPropertiesString(properties);
        Validate.notNull(builder, "Null Standard Service Registry Builder returned. Aborting.");
        LOGGER.trace("Standard Registry Builder built with values from configuration:\n{}",props);
        return builder;
    }

    private String getPropertiesString(Properties properties)
    {
        final StringBuilder props = new StringBuilder("");
        properties.stringPropertyNames().forEach((key) ->{
            props.append(String.format("\t%s=>%s",key,properties.getProperty(key)));
        });
        return props.toString();
    }

    private Configuration getConfiguration()
    {
        final Configuration configuration =
                (null == this.configuration) ?
                        new Configuration().configure() : this.configuration;
        Validate.notNull(configuration, "Null configuration returned. Aborting.");
        LOGGER.trace("Configuration instance configured.");
        return configuration;
    }
}
