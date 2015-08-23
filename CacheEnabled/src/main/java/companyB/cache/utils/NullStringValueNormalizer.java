package companyB.cache.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * While in-memory caches can handle null values, unpredictable behaviors may be experienced when using
 * other systems such as Databases and Redis. This class has been written so as to guarantee that (a) truly null values
 * such as blank strings are replaced with a sensible universally accessible default value, and (b) if this default value
 * exists in the cache it is replaced with 'null' upon retrieval.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.2.0
 */
public class NullStringValueNormalizer
{
    public static final String NULL_STRING = "[NULL_STRING]";

    /**
     * @param value String to be evaluated.
     * @return The original value if it is not null or an empty string, else "[NULL_STRING]".
     * @since 2.2.0
     */
    public String cleanNullStringValue(String value)
    {
        return StringUtils.isBlank(value) ? NULL_STRING : value;
    }

    /**
     * @param value String to be evaluated.
     * @return The original value if it is not "[NULL_STRING]" else null.
     * @since 2.2.0
     */
    public String dirtyNullStringValue(String value)
    {
        return StringUtils.equals(NULL_STRING,value) ? null : value;
    }
}
