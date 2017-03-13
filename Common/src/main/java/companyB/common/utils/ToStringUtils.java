package companyB.common.utils;

import java.util.Map;

/**
 * Provides custom representations of Iterables and Maps.
 * @author Charles Burell (deltafront@gmail.com)
 * @version 1.0.0
 */
@SuppressWarnings({"unchecked"})
public class ToStringUtils extends UtilityBase
{
    /**
     * Writes out an iterable instance to a string.
     * @param iterable Iterable to be converted to string representation.
     * @param <E> Type parameter
     * @return String representation of Iterable.
     */
    public <E> String iterableToString(Iterable<E> iterable)
    {
        final StringBuilder result = new StringBuilder("[");
        if(null != iterable)
            iterable.forEach((out)->appendToIterable(result, out));
        final String output = stripLastComma(result,"]");
        LOGGER.trace("Returning string representation of iterable\n{}",output);
        return output;
    }

    private <E> void appendToIterable(StringBuilder result, E out)
    {
        String temp = getString(out);
        result.append(String.format("%s,",temp));
    }

    private <E> String getString(E out)
    {
        return (out instanceof Iterable) ? iterableToString((Iterable)out) :
                            (out instanceof Map) ? mapToString((Map)out) : String.valueOf(out);
    }

    /**
     * Writes out an instance of Map to string.
     * @param map Instance to be written out.
     * @param <Key> Type Parameter.
     * @param <Value> Type Parameter.
     * @return String representation of Map instance.
     */
    public <Key,Value> String mapToString(Map<Key, Value> map)
    {
        final StringBuilder out = new StringBuilder("{");
        if(null != map)
            map.keySet().forEach((key)->appendToMapping(map, out, key));
        return stripLastComma(out, "}");
    }

    private <Key, Value> void appendToMapping(Map<Key, Value> map, StringBuilder out, Key key)
    {
        Value value = map.get(key);
        String temp = getString((value));
        out.append(String.format("%s:%s,",key,temp));
    }

    private String stripLastComma(StringBuilder stringBuffer, String finalAppend)
    {
        String output = stringBuffer.toString();
        if (output.contains(","))
            output = output.substring(0,output.lastIndexOf(","));
        return String.format("%s%s",output,finalAppend);
    }
}
