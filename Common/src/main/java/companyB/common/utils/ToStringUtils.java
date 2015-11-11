package companyB.common.utils;

import java.util.Map;

/**
 * Provides custom representations of Iterables and Maps.
 * @author Charles Burell (deltafront@gmail.com)
 * @since  1.0.0
 */
@SuppressWarnings({"PMD.UselessParentheses", "unchecked"})
public class ToStringUtils extends UtilityBase
{
    /**
     * Writes out an iterable instance to a string.
     * @param iterable Iterable to be converted to string representation.
     * @param <E> Type parameter
     * @return String representation of Iterable.
     * @since 1.0.0
     */
    public <E> String iterableToString(Iterable<E> iterable)
    {
        String result = (null != iterable) ? GSON.toJson(iterable): "[]";
        LOGGER.trace(String.format("Returning string representation of iterable\n%s",result));
        return result;
    }

    /**
     * Writes out an instance of Map to string.
     * @param map Instance to be written out.
     * @param <Key> Type Parameter.
     * @param <Value> Type Parameter.
     * @return String representation of Map instance.
     * @since 1.0.0
     */
    public <Key,Value> String mapToString(Map<Key, Value> map)
    {
        String out  =(null != map) ?  GSON.toJson(map) : "{}";
        LOGGER.trace(String.format("Returning string representation of map\n%s",out));
        return out;
    }
}
