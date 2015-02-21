package companyB.decorated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by chburrell on 2/9/15.
 */
@SuppressWarnings("ALL")
public abstract class Converter
{
    private final static Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private final static Class[] _supported = new Class[]
            {   Long.class,long.class,String.class, Integer.class, int.class,
                short.class,Short.class, Double.class, double.class,
                Boolean.class, boolean.class, Byte.class, byte.class,
                char.class, Character.class, BigDecimal.class, BigInteger.class
            };
    private final static Class[] _numbers = new Class[]
            {   Long.class,long.class,Integer.class, int.class,
                short.class,Short.class, Double.class, double.class,
            };
    private final static  String[] _true = new String[]{"t", "true", "y", "yes","1"};
    private final static  String[] _false = new String[]{"f", "false", "n", "no","0"};

    public static List<Class> supportedClasses;
    public static List<String> trueValues;
    public static List<String>falseValues;
    public static List<Class> numberClasses;

    static
    {

        if (null == supportedClasses)
        {
            supportedClasses = new LinkedList<>();
            Collections.addAll(supportedClasses,_supported);
        }
        if(null == trueValues)
        {
            trueValues = new LinkedList<>();
            Collections.addAll(trueValues,_true);
        }
        if(null == falseValues)
        {
            falseValues = new LinkedList<>();
            Collections.addAll(falseValues,_false);
        }
        if(null == numberClasses)
        {
            numberClasses = new LinkedList<>();
            Collections.addAll(numberClasses, _numbers);
        }
    }

    public static boolean isSupported(Class c)
    {
        return supportedClasses.contains(c);
    }
    public static boolean isNumberType(Class c)
    {
        return numberClasses.contains(c);
    }
    public static boolean isBigType(Class c)
    {
        return BigDecimal.class.equals(c) || BigInteger.class.equals(c);
    }
    public static boolean isBoolean(Class c)
    {
        return boolean.class.equals(c) || Boolean.class.equals(c);
    }
    public static boolean isByte(Class c)
    {
        return byte.class.equals(c) || Byte.class.equals(c);
    }
    public static boolean isCharOrString(Class c)
    {
        return  char.class.equals(c) ||
                Character.class.equals(c) ||
                String.class.equals(c);
    }
    public static Object convertToByte(String value)
    {
        Object out = Byte.parseByte(value);
        logOut(out);
        return out;

    }
    public static Object convertToStringOrChar(String value, Class classType)
    {
        Object out = char.class.equals(classType) || Character.class.equals(classType) ?
                value.charAt(0) : value;
        logOut(out);
        return out;
    }
    public static Object convertToBig(String value, Class classType)
    {
        Object out = BigDecimal.class.equals(classType) ? new BigDecimal(value) :
                (BigInteger.class.equals(classType)) ? new BigInteger(value) :
                        null;
        logOut(out);
        return out;
    }
    public static Object convertToNumber(String value, Class classType)
    {
        Object out = value;
        if(null != value)
        {
            if (long.class.equals(classType) || Long.class.equals(classType))
            {
                out = Long.parseLong(value);
            }
            else if (short.class.equals(classType) || Short.class.equals(classType))
            {
                out = Short.parseShort(value);
            }
            else if (double.class.equals(classType) || Double.class.equals(classType))
            {
                out = Double.parseDouble(value);
            }
            else if (int.class.equals(classType) || Integer.class.equals(classType))
            {
                out = Integer.parseInt(value);
            }
        }
        logOut(out);
        return out;
    }
    public static Object convertToBoolean(String value)
    {
        Object out = null;
        if (trueValues.contains(value.toLowerCase()))
        {
            out = Boolean.TRUE;
        }
        else if (falseValues.contains(value.toLowerCase()))
        {
            out = Boolean.FALSE;
        }
        logOut(out);
        return out;
    }
    private static void logOut(Object out)
    {
        String outToString = (null == out) ? "" : String.valueOf(out);
        String className = (null == out) ? "Null" : out.getClass().getCanonicalName();
        LOGGER.debug(String.format("Returning value %s [%s].", outToString, className));
    }
}
