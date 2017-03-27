package companyB.cache.impl.redis;

import companyB.cache.AbstractExternalCache;
import companyB.cache.ExternalCache;
import org.apache.commons.lang3.Validate;
import redis.clients.jedis.Jedis;

/**
 * Redis - based implementation of ExternalCache.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class RedisExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private final String host;
    private final Integer port;
    private final Integer db;
    private final String cacheName;
    private Jedis connection;

    /**
     * Default constructor.
     * @param host Host that Redis is running on.
     * @param port Port that Redis is listening on.
     * @param db Database to be used.
     * @param cacheName Name of this cache.
     */
    public RedisExternalCache(String host, Integer port, Integer db, String cacheName)
    {
        super(cacheName);
        Validate.notEmpty(host, "Redis Host must be provided.");
        Validate.notNull(port, "Redis Port must be provided.");
        Validate.notNull(db, "Redis db must be provided.");
        this.host = host;
        this.db = db;
        this.port = port;
        this.cacheName = cacheName;
    }

    @Override
    public void insert(String key, String value)
    {
        Validate.notBlank(key, "Non-blank key must be provided.");
        getConnection();
        connection.set(key, normalizer.cleanNullStringValue(value));
    }

    @Override
    public String retrieve(String key)
    {
        Validate.notBlank(key,"Non-blank key must be provided.");
        getConnection();
        return normalizer.dirtyNullStringValue(connection.get(key));
    }

    @Override
    public void clear()
    {
        getConnection();
        connection.flushDB();
    }

    @Override
    public String remove(String key)
    {
        Validate.notBlank(key,"Non-blank key must be provided.");
        getConnection();
        final String value = normalizer.dirtyNullStringValue(connection.get(key));
        connection.del(key);
        return value;
    }


    @Override
    public String getName()
    {
        return cacheName;
    }

    /**
     * Pings the service to make sure that it is alive.
     * @return Result of the ping from the Redis instance.
     */
    public String ping()
    {
        getConnection();
        return connection.ping();
    }

    private void getConnection()
    {
        if(null != connection)connection.quit();
        connection = new Jedis(host,port);
        connection.select(db);
    }

}
