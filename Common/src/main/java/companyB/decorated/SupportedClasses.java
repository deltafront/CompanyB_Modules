package companyB.decorated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Master list of all of the supported classes.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version  1.0.0
 */
public class SupportedClasses
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SupportedClasses.class);
    private static final Class[] _supported = new Class[]
     {   Long.class,long.class,String.class, Integer.class, int.class,
     short.class,Short.class, Double.class, double.class,
     Boolean.class, boolean.class, Byte.class, byte.class,
     char.class, Character.class, BigDecimal.class, BigInteger.class
     };
    public static final List<Class>supportedClasses;
    static
    {
        supportedClasses = new LinkedList<>();
        Collections.addAll(supportedClasses,_supported);
    }

    /**
     * @return String containing all of the currently supported classes.
     */
    public String getSupportedClassesList()
    {
        final StringBuilder s = new StringBuilder("[");
        for(final Class c : supportedClasses) s.append(String.format("%s,",c.getCanonicalName()));
        int end = s.lastIndexOf(",");
        s.append("]");
        final String out = s.substring(0,end);
        LOGGER.trace("Returning list of supported classes:\n{}]",out);
        return out + "]";
    }
}
