package companyB.common.utils;

import java.util.Map;
import java.util.Set;

/**
 * Provides custom representations of Iterables and Maps.
 * @author Charles Burell (deltafront@gmail.com)
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public abstract class ToStringUtils
{
    /**
     * Writes out an iterable instance to a string.
     * @param iterable Iterable to be converted to string representation.
     * @param <E> Type parameter
     * @return String representation of Iterable.
     * @since 1.0
     */
    public static<E> String iterableToString(Iterable<E> iterable)
    {
        StringBuilder stringBuilder = new StringBuilder("[");
        if(null != iterable)
        {
            for( E out : iterable)
            {
                String temp = (out instanceof Iterable) ? iterableToString((Iterable)out) :
                        (out instanceof Map) ? mapToString((Map)out) : String.valueOf(out);
                stringBuilder.append(String.format("%s,",temp));
            }
        }
        stringBuilder.trimToSize();
        String out = stringBuilder.toString();
        if(out.contains(","))
        {
            out = out.substring(0,out.lastIndexOf(","));
        }
        return String.format("%s]",out);
    }

    /**
     * Writes out an instance of Map to string.
     * @param map Instance to be written out.
     * @param <Key> Type Parameter.
     * @param <Value> Type Parameter.
     * @return String representation of Map instance.
     * @since 1.0
     */
    public static<Key,Value> String mapToString(Map<Key, Value> map)
    {
        StringBuilder stringBuilder = new StringBuilder("{");
        if(null != map)
        {
            Set<Key>keys = map.keySet();
            for(Key key : keys)
            {
                Value value = map.get(key);
                String temp = (value instanceof Iterable) ? iterableToString((Iterable)value) :
                        (value instanceof Map) ? mapToString((Map)value) : String.valueOf(value);
                stringBuilder.append(String.format("%s:%s,",key,temp));
            }
        }
        stringBuilder.trimToSize();
        String out = stringBuilder.toString();
        if(out.contains(","))
        {
            out = out.substring(0,out.lastIndexOf(","));
        }
        return String.format("%s}",out);
    }
}
