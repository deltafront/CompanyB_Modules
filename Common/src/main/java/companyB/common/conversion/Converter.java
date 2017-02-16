package companyB.common.conversion;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Converts Strings representations into various supported datatypes.
 * The types supported are:
 * <ul>
 * <li>java.lang.Boolean</li>
 * <li>java.lang.String</li>
 * <li>java.lang.Integer</li>
 * <li>java.lang.Short</li>
 * <li>java.lang.Long</li>
 * <li>java.lang.Double</li>
 * <li>java.lang.Byte</li>
 * <li>java.lang.Character</li>
 * <li>java.math.BigDecimal</li>
 * <li>java.math.BigInteger</li>
 * <li>boolean</li>
 * <li>char</li>
 * <li>int</li>
 * <li>long</li>
 * <li>short</li>
 * <li><double/li>
 * <li>byte</li>
 * </ul>
 *
 * @author Charles Burrell (deltafront@gmail.com)
 * @since  1.0.0
 */
@SuppressWarnings("ALL")
public class Converter
{
    private final static Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private final static Class[] _supported = new Class[]
            {Long.class, long.class, String.class, Integer.class, int.class,
                    short.class, Short.class, Double.class, double.class,
                    Boolean.class, boolean.class, Byte.class, byte.class,
                    char.class, Character.class, BigDecimal.class, BigInteger.class
            };
    private final static Class[] _numbers = new Class[]
            {Long.class, long.class, Integer.class, int.class,
                    short.class, Short.class, Double.class, double.class,
            };
    private final static String[] _true = new String[]{"t", "true", "y", "yes", "1"};
    private final static String[] _false = new String[]{"f", "false", "n", "no", "0"};

    /**
     * All supported classes.
     *
     * @since 1.0.0
     */
    public static List<Class> supportedClasses;
    /**
     * String values which constitute a boolean value of 'true'.
     *
     * @since 1.0.0
     */
    public static List<String> trueValues;
    /**
     * String values which constitute a boolean value of 'false'.
     *
     * @since 1.0.0
     */
    public static List<String> falseValues;
    /**
     * All of the valid number classes. This list includes:
     * <ul>
     * <li>java.lang.Integer</li>
     * <li>java.lang.Short</li>
     * <li>java.lang.Long</li>
     * <li>java.lang.Double</li>
     * <li>int</li>
     * <li>long</li>
     * <li>short</li>
     * <li><double/li>
     * </ul>
     * @since 1.0.0
     */
    public static List<Class> numberClasses;

    static
    {

        supportedClasses = new LinkedList<>();
        Collections.addAll(supportedClasses, _supported);
        trueValues = new LinkedList<>();
        Collections.addAll(trueValues, _true);
        falseValues = new LinkedList<>();
        Collections.addAll(falseValues, _false);
        numberClasses = new LinkedList<>();
        Collections.addAll(numberClasses, _numbers);
    }

    /**
     * Returns whether or not the indicated class is supported.
     * @param c Class to be evaluated.
     * @return Whether or not the indicated class is supported.
     * @since 1.0.0
     */
    public boolean isSupported(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return supportedClasses.contains(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a number type.
     * @since 1.0.0
     */
    public boolean isNumberType(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return numberClasses.contains(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return Is either a BigDecimal or BigInteger.
     * @since 1.0.0
     */
    public boolean isBigType(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return BigDecimal.class.equals(c) || BigInteger.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Boolean type.
     * @since 1.0.0
     */
    public boolean isBoolean(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return boolean.class.equals(c) || Boolean.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Byte type.
     * @since 1.0.0
     */
    public boolean isByte(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return byte.class.equals(c) || Byte.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If the class represents a Character type or is a string.
     * @since 1.0.0
     */
    public boolean isCharOrString(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return char.class.equals(c) ||
                Character.class.equals(c) ||
                String.class.equals(c);
    }

    /**
     * @param value String value to be converted.
     * @return Byte representation.
     * @since 1.0.0
     */
    public Byte convertToByte(String value)
    {
        Validate.notNull(value,"Value is required.");
        final Byte out = Byte.parseByte(value);
        logOut(out);
        return out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either Character or String.
     * @return String or Character representation.
     * @since 1.0.0
     */
    public <T> T convertToStringOrChar(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
        Object out = null;
        if(char.class.equals(classType))out = new Character(value.charAt(0));
        if(Character.class.equals(classType))out = new Character(value.charAt(0));
        if(String.class.equals(classType))out = value;
        logOut(out);
        return (T)out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either BigDecimal or BigInteger.
     * @return Either BigDecimal or BigInteger representaion.
     * @since 1.0.0
     */
    public <T> T convertToBig(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
        Object out = BigDecimal.class.equals(classType) ?
                new BigDecimal(value) :
                BigInteger.class.equals(classType) ?
                        new BigInteger(value) : null;
        logOut(out);
        return (T) out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Numeric Class type.
     * @return Numeric representation.
     * @since 1.0.0
     */
    public <T> T convertToNumber(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
        Object out = value;
        out = getLong(value, classType, out);
        out = getShort(value, classType, out);
        out = getDouble(value, classType, out);
        out = getInteger(value, classType, out);
        logOut(out);
        return (T) out;
    }

    /**
     * @param value String value to be converted.
     * @return Boolean representation.
     * @since 1.0.0
     */
    public Boolean convertToBoolean(String value)
    {
        Validate.notNull(value,"Value is required.");
        final Boolean out = trueValues.contains(value.toLowerCase()) ?
                Boolean.TRUE :
                falseValues.contains(value.toLowerCase()) ?
                        Boolean.FALSE : null;
        logOut(out);
        return out;
    }

    private void logOut(Object out)
    {
        final String outToString = coercePossibleNullStringValue(out);
        final String className = coercePossibleNullClassName(out);
        LOGGER.debug("Returning value {} [{}].", outToString, className);
    }
    private String coercePossibleNullStringValue(Object object)
    {
        return (null!=object) ?
                String.valueOf(object) :
                "null";
    }
    private String coercePossibleNullClassName(Object object)
    {
        return (null != object) ?
        object.getClass().getCanonicalName() :
        "null";
    }
    private <T> Object getInteger(String value, Class<T> classType, Object out)
    {
        return int.class.equals(classType) || Integer.class.equals(classType) ?
                Integer.parseInt(value) :
                out;
    }

    private <T> Object getDouble(String value, Class<T> classType, Object out)
    {
        return double.class.equals(classType) || Double.class.equals(classType) ?
                Double.parseDouble(value) :
                out;
    }

    private <T> Object getShort(String value, Class<T> classType, Object out)
    {
        return  short.class.equals(classType) || Short.class.equals(classType) ?
                Short.parseShort(value) :
                out;
    }

    private <T> Object getLong(String value, Class<T> classType, Object out)
    {
        return  long.class.equals(classType) || Long.class.equals(classType) ?
                Long.parseLong(value) :
                out;
    }
}
