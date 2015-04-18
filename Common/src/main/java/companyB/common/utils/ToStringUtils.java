package companyB.common.utils;

import java.util.Map;
import java.util.Set;

/**
 * Provides custom representations of Iterables and Maps.
 * @author Charles Burell (deltafront@gmail.com)
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public class ToStringUtils
{
    /**
     * Writes out an iterable instance to a string.
     * @param iterable Iterable to be converted to string representation.
     * @param <E> Type parameter
     * @return String representation of Iterable.
     * @since 1.0
     */
    public <E> String iterableToString(Iterable<E> iterable)
    {
        String result = ("[");
        if(null != iterable)
        {
            for( E out : iterable)
            {
                String temp = (out instanceof Iterable) ? iterableToString((Iterable)out) :
                        (out instanceof Map) ? mapToString((Map)out) : String.valueOf(out);
                result += (String.format("%s,",temp));
            }
        }
        if(result.contains(","))
            result = result.substring(0,result.lastIndexOf(","));
        return String.format("%s]",result);
    }

    /**
     * Writes out an instance of Map to string.
     * @param map Instance to be written out.
     * @param <Key> Type Parameter.
     * @param <Value> Type Parameter.
     * @return String representation of Map instance.
     * @since 1.0
     */
    public <Key,Value> String mapToString(Map<Key, Value> map)
    {
        String out  = ("{");
        if(null != map)
        {
            Set<Key>keys = map.keySet();
            for(Key key : keys)
            {
                Value value = map.get(key);
                String temp = (value instanceof Iterable) ? iterableToString((Iterable)value) :
                        (value instanceof Map) ? mapToString((Map)value) : String.valueOf(value);
                out += (String.format("%s:%s,",key,temp));
            }
        }
        if(out.contains(","))
            out = out.substring(0,out.lastIndexOf(","));
        return String.format("%s}",out);
    }
}
