package companyB.cache.impl.guava;

import com.google.common.cache.Cache;
import companyB.cache.AbstractExternalCache;
import companyB.cache.ExternalCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of ExternalCache that uses Google Guava default cache.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class GuavaExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private final Cache<String,String> cache;
    /**
     * Using this constructor will return a Cache that has an upper maximum limit of items that can be held. When this
     * limit is about to be reached, the cache will evict items based on when they were last accessed.
     * @param name Name of the cache. Required.
     * @param maxSize Maximum size of cache. Required. Must be an integer greater that '0'.
     */
    public GuavaExternalCache(String name, Integer maxSize)
    {
        this(name,maxSize,null,null,null);
    }

    /**
     * @param name Name of the cache. Required.
     * @param duration Duration of 'timeUnit', after which entries will be evicted. Required. Must be greater than '0'.
     * @param timeUnit java.util.concurrent.TimeUnit
     * @param expireAfterWrite If this is set to true, then entries will be expired after their creation or replacement.
     *                         If this is set to false, then entries will  be expired after their creation or access.
     */
    public GuavaExternalCache(String name, Long duration, TimeUnit timeUnit, boolean expireAfterWrite)
    {
        this(name,null,duration,timeUnit,expireAfterWrite);
    }

    @Override
    public void insert(String key, String value)
    {
        Validate.notBlank(key,"Key must be provided.");
        cache.put(key,normalizer.cleanNullStringValue(value));
    }

    @Override
    public String retrieve(String key)
    {
        String value = cache.getIfPresent(key);
        if(!StringUtils.isBlank(value)) value = getEncryptedString(key);
        return value;
    }

    @Override
    public void clear()
    {
        cache.invalidateAll();
    }

    @Override
    public String remove(String key)
    {
        String value = cache.getIfPresent(key);
        if(null != value) value = removeAndGetValue(key, value);
        return value;
    }

    @Override
    public String getName()
    {
        return name;
    }

    private GuavaExternalCache(String name,Integer maxSize,Long duration,TimeUnit timeUnit,Boolean expireAfterWrite)
    {
        super(name);
        if(null == expireAfterWrite) this.cache = GuavaCacheFactory.getMaxSizeCache(maxSize);
        else if(expireAfterWrite) this.cache = GuavaCacheFactory.getExpireAfterWriteCache(duration, timeUnit);
        else this.cache = GuavaCacheFactory.getExpireAfterAccessCache(duration, timeUnit);
        Validate.notNull(this.cache,"Cache has not been built. Aborting.");
    }
    private String removeAndGetValue(String key, String value)
    {
        final String val = normalizer.dirtyNullStringValue(value);
        cache.invalidate(key);
        return val;
    }

    private String getEncryptedString(String key)
    {
        return normalizer.dirtyNullStringValue(cache.getIfPresent(key));
    }
}
