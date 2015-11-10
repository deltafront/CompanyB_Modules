package companyB.cache.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.TimeUnit;

/**
 * Contains convenience methods for constructing Guava-based caches.
 * @author Charles A. Burrell (deltafront@gmail.com)
 * @since 2.1.0
 */
public class GuavaUtils extends UtilityBase
{
    /**
     * @param duration Value for time unit after last read or access a entry is set to expire.
     * @param timeUnit Time unit used for duration above.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose entries are configured to expire a certain amount of time after the last write or access.
     * @since 2.1.0
     */
    public  <Key,Value>Cache<Key,Value> getExpireAfterAccessCache(Long duration, TimeUnit timeUnit)
    {
        validateTimeExpiry(duration, timeUnit);
        LOGGER.trace(String.format("All entries will expire %d %s(s) after last write or access.",duration,timeUnit.name()));
        return CacheBuilder.newBuilder().expireAfterAccess(duration,timeUnit).build();
    }

    /**
     * @param duration Value for time unit after write a entry is set to expire.
     * @param timeUnit Time unit used for duration above.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose entries are configured to expire a certain amount of time after the last write.
     * @since 2.1.0
     */
    public  <Key,Value> Cache<Key,Value> getExpireAfterWriteCache(Long duration, TimeUnit timeUnit)
    {
        validateTimeExpiry(duration, timeUnit);
        LOGGER.trace(String.format("All entries will expire %d %s(s) after last write.",duration,timeUnit.name()));
        return CacheBuilder.newBuilder().expireAfterWrite(duration, timeUnit).build();
    }

    /**
     * @param maxSize Max size of cache.
     * @param <Key> Parametrised Key.
     * @param <Value> Parametrised Value.
     * @return Guava cache whose maximum size is as configured.
     * @since 2.1.0
     */
    public  <Key,Value> Cache<Key,Value> getMaxSizeCache(Integer maxSize)
    {
        validateMaxSizeInfo(maxSize);
        LOGGER.trace(String.format("Maximum size of cache is '%d' item(s).",maxSize));
        return CacheBuilder.newBuilder().maximumSize(maxSize).build();
    }
    private void validateMaxSizeInfo(Integer maxSize)
    {
        Validate.notNull(maxSize, "Maximum size of cache must be specified.");
        Validate.isTrue(maxSize > 0, "Maximum size must be greater than zero.");
    }

    private void validateTimeExpiry(Long duration, TimeUnit timeUnit)
    {
        Validate.notNull(duration, "Time duration must be specified.");
        Validate.isTrue(duration > 0,"Time Duration must be greater than zero.");
        Validate.notNull(timeUnit,"TimeUnit must be specified.");
    }

}
