package companyB.cache.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import companyB.common.utils.UtilityBase;

import java.util.concurrent.TimeUnit;

/**
 * Contains convenience methods for constructing Guava-based caches.
 * @author Charles A. Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class GuavaUtils extends UtilityBase
{
    /**
     * @param duration Value for time unit after last read or access a entry is set to expire.
     * @param timeUnit Time unit used for duration above.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose entries are configured to expire a certain amount of time after the last write or access.
     */
    public  <Key,Value>Cache<Key,Value> getExpireAfterAccessCache(Long duration, TimeUnit timeUnit)
    {
        return CacheBuilder.newBuilder().expireAfterAccess(duration,timeUnit).build();
    }

    /**
     * @param duration Value for time unit after write a entry is set to expire.
     * @param timeUnit Time unit used for duration above.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose entries are configured to expire a certain amount of time after the last write.
     */
    public  <Key,Value> Cache<Key,Value> getExpireAfterWriteCache(Long duration, TimeUnit timeUnit)
    {
       return CacheBuilder.newBuilder().expireAfterWrite(duration, timeUnit).build();
    }

    /**
     * @param maxSize Max size of cache.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose maximum size is as configured.
     */
    public  <Key,Value> Cache<Key,Value> getMaxSizeCache(Integer maxSize)
    {
        return CacheBuilder.newBuilder().maximumSize(maxSize).build();
    }
}
