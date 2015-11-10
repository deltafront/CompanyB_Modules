package companyB.common.cache.impl.guava;

import com.google.common.cache.Cache;
import companyB.common.cache.AbstractExternalCache;
import companyB.common.cache.ExternalCache;
import companyB.common.cache.utils.GuavaUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of ExternalCache that uses Google Guava default cache.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.1.0
 */
public class GuavaExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private final Cache<String,String> cache;
    /**
     * Using this constructor will return a Cache that has an upper maximum limit of items that can be held. When this
     * limit is about to be reached, the cache will evict items based on when they were last accessed.
     * @param name Name of the cache. Required.
     * @param maxSize Maximum size of cache. Required. Must be an integer greater that '0'.
     * @since 2.1.0
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
     * @since 2.2.0
     */
    public GuavaExternalCache(String name, Long duration, TimeUnit timeUnit, boolean expireAfterWrite)
    {
        this(name,null,duration,timeUnit,expireAfterWrite);
    }
    private GuavaExternalCache(String name,Integer maxSize,Long duration,TimeUnit timeUnit,Boolean expireAfterWrite)
    {
        super(name);
        final GuavaUtils guavaUtils = new GuavaUtils();
        if(null == expireAfterWrite) this.cache = guavaUtils.getMaxSizeCache(maxSize);
        else if(expireAfterWrite) this.cache = guavaUtils.getExpireAfterWriteCache(duration, timeUnit);
        else this.cache = guavaUtils.getExpireAfterAccessCache(duration, timeUnit);
        Validate.notNull(this.cache,"Cache has not been built. Aborting.");
    }
    @Override
    public void insert(String key, String value)
    {
        Validate.notBlank(key,"Key must be provided.");
        cache.put(key,normalizer.cleanNullStringValue(value));
        LOGGER.trace(String.format("Associated %s=>%s in cache.",key,value));
    }

    @Override
    public String retrieve(String key)
    {
        String value = cache.getIfPresent(key);
        if(!StringUtils.isBlank(value))
        {
            value = normalizer.dirtyNullStringValue(cache.getIfPresent(key));
            LOGGER.trace(String.format("Returning value %s for key %s.",value,key));
        }
        else LOGGER.trace(String.format("No value found for key %s.",key));
        return value;
    }

    @Override
    public void clear()
    {
        LOGGER.trace("Clearing all entries in this cache.");
        cache.invalidateAll();
    }

    @Override
    public String remove(String key)
    {
        String value = cache.getIfPresent(key);
        if(null != value)
        {
            value = normalizer.dirtyNullStringValue(value);
            LOGGER.trace(String.format("Returning and removing value %s for key %s.",value,key));
            cache.invalidate(key);
        }
        else LOGGER.trace(String.format("No value found for key %s.",key));
        return value;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
