package companyB.cache.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by chburrell on 5/19/15.
 */
public class GuavaUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(GuavaUtils.class);

    public  <Key,Value>Cache<Key,Value> getExpireAfterAccessCache(Long duration, TimeUnit timeUnit)
    {
        validateTimeExpiry(duration, timeUnit);
        LOGGER.trace(String.format("All entries will expire %d %s(s) after last write or access.",duration,timeUnit.name()));
        return CacheBuilder.newBuilder().expireAfterAccess(duration,timeUnit).build();
    }

    public  <Key,Value> Cache<Key,Value> getExpireAfterWriteCache(Long duration, TimeUnit timeUnit)
    {
        validateTimeExpiry(duration, timeUnit);
        LOGGER.trace(String.format("All entries will expire %d %s(s) after last write.",duration,timeUnit.name()));
        return CacheBuilder.newBuilder().expireAfterWrite(duration, timeUnit).build();
    }
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
