package companyB.common.conversion;

import org.apache.commons.lang3.Validate;

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
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
public class Converter
{
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
     */
    public static List<Class> supportedClasses;
    /**
     * String values which constitute a boolean value of 'true'.
     */
    public static List<String> trueValues;
    /**
     * String values which constitute a boolean value of 'false'.
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
     */
    public boolean isSupported(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return supportedClasses.contains(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a number type.
     */
    public boolean isNumberType(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return numberClasses.contains(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return Is either a BigDecimal or BigInteger.
     */
    public boolean isBigType(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return BigDecimal.class.equals(c) || BigInteger.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Boolean type.
     */
    public boolean isBoolean(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return boolean.class.equals(c) || Boolean.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Byte type.
     */
    public boolean isByte(Class c)
    {
        Validate.notNull(c,"Class is required.");
        return byte.class.equals(c) || Byte.class.equals(c);
    }

    /**
     * @param c Class to be evaluated.
     * @return If the class represents a Character type or is a string.
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
     */
    public Byte convertToByte(String value)
    {
        Validate.notNull(value,"Value is required.");
        final Byte out = Byte.parseByte(value);
        return out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either Character or String.
     * @return String or Character representation.
     */
    public <T> T convertToStringOrChar(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
        Object out = null;
        if(char.class.equals(classType))out = new Character(value.charAt(0));
        if(Character.class.equals(classType))out = new Character(value.charAt(0));
        if(String.class.equals(classType))out = value;
        return (T)out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either BigDecimal or BigInteger.
     * @return Either BigDecimal or BigInteger representaion.
     */
    public <T> T convertToBig(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
        Object out = BigDecimal.class.equals(classType) ?
                new BigDecimal(value) :
                BigInteger.class.equals(classType) ?
                        new BigInteger(value) : null;
        return (T) out;
    }

    /**
     * @param value     String value to be converted.
     * @param classType Numeric Class type.
     * @return Numeric representation.
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
        return (T) out;
    }

    /**
     * @param value String value to be converted.
     * @return Boolean representation.
     */
    public Boolean convertToBoolean(String value)
    {
        Validate.notNull(value,"Value is required.");
        final Boolean out = trueValues.contains(value.toLowerCase()) ?
                Boolean.TRUE :
                falseValues.contains(value.toLowerCase()) ?
                        Boolean.FALSE : null;
        return out;
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
