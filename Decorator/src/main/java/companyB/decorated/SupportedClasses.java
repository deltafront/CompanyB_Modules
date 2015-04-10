package companyB.decorated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Master list of all of the supported classes.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class SupportedClasses
{
    private static Class[] _supported = new Class[]
     {   Long.class,long.class,String.class, Integer.class, int.class,
     short.class,Short.class, Double.class, double.class,
     Boolean.class, boolean.class, Byte.class, byte.class,
     char.class, Character.class, BigDecimal.class, BigInteger.class
     };
    public static List<Class>supportedClasses;
    static
    {
        supportedClasses = new LinkedList<>();
        Collections.addAll(supportedClasses,_supported);
    }

    /**
     * @return String containing all of the currently supported classes.
     * @since 1.0
     */
    public String getSupportedClassesList()
    {
        StringBuilder stringBuilder = new StringBuilder("[");
        for(Class c : supportedClasses)
        {
            stringBuilder.append(String.format("%s,",c.getCanonicalName()));
        }
        stringBuilder.trimToSize();
        String out = stringBuilder.toString();
        int end = out.lastIndexOf(",");
        out = out.substring(0,end);
        out += "]";
        return out;
    }
}
