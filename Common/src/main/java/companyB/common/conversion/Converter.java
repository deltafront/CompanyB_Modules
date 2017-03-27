package companyB.common.conversion;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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
 * @author Charles Burrell (deltafront@gmail.com)
 */
@SuppressWarnings("ALL")
public class Converter
{
    private static List<String> trueValues;
    private static List<String> falseValues;
    private static List<Class> numberClasses;
    private static Map<Class,Function<String,Object>>converterFunctionsMappings;

    static
    {

        trueValues = Arrays.asList("y,yes,t,true,1".split(","));
        falseValues = Arrays.asList("n,no,f,false,0".split(","));
        numberClasses = Arrays.asList(Long.class, long.class, Integer.class, int.class);

        converterFunctionsMappings = new HashMap<>();
        converterFunctionsMappings.put(Long.class,(value)-> Long.parseLong(value));
        converterFunctionsMappings.put(long.class,(value)-> Long.parseLong(value));
        converterFunctionsMappings.put(Integer.class,(value)-> Integer.parseInt(value));
        converterFunctionsMappings.put(int.class,(value)-> Integer.parseInt(value));
        converterFunctionsMappings.put(Short.class,(value)-> Short.parseShort(value));
        converterFunctionsMappings.put(short.class,(value)-> Short.parseShort(value));
        converterFunctionsMappings.put(Double.class,(value)-> Double.parseDouble(value));
        converterFunctionsMappings.put(double.class,(value)-> Double.parseDouble(value));
        converterFunctionsMappings.put(BigDecimal.class,(value)-> new BigDecimal(value));
        converterFunctionsMappings.put(BigInteger.class,(value)-> new BigInteger(value));
        converterFunctionsMappings.put(char.class, (value)->new Character(value.charAt(0)));
        converterFunctionsMappings.put(Character.class, (value)->new Character(value.charAt(0)));
        converterFunctionsMappings.put(String.class, (value)->value);
        converterFunctionsMappings.put(Byte.class,(value)->Byte.parseByte(value));
        converterFunctionsMappings.put(byte.class,(value)->Byte.parseByte(value));
        converterFunctionsMappings.put(Boolean.class,(value)-> trueValues.contains(value) ? true : falseValues.contains(value) ? false : null);
        converterFunctionsMappings.put(boolean.class,(value)-> trueValues.contains(value) ? true : falseValues.contains(value) ? false : null);
    }

    /**
     * Returns whether or not the indicated class is supported.
     * @param c Class to be evaluated.
     * @return Whether or not the indicated class is supported.
     */
    public boolean isSupported(Class c)
    {
        return isClass(c,(_class)->converterFunctionsMappings.containsKey(c));
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a number type.
     */
    public boolean isNumberType(Class c)
    {
        return isClass(c,(_class)->numberClasses.contains(_class));
    }

    /**
     * @param c Class to be evaluated.
     * @return Is either a BigDecimal or BigInteger.
     */
    public boolean isBigType(Class c)
    {
        return isClass(c, (_class)->BigDecimal.class.equals(c) || BigInteger.class.equals(c));
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Boolean type.
     */
    public boolean isBoolean(Class c)
    {
        return isClass(c,(_class)->boolean.class.equals(c) || Boolean.class.equals(c));
    }

    /**
     * @param c Class to be evaluated.
     * @return If class represents a Byte type.
     */
    public boolean isByte(Class c)
    {
        return isClass(c,(_class)->byte.class.equals(c) || Byte.class.equals(c));
    }

    /**
     * @param c Class to be evaluated.
     * @return If the class represents a Character type or is a string.
     */
    public boolean isCharOrString(Class c)
    {
        return isClass(c,(_class)->char.class.equals(c) ||
                Character.class.equals(c) ||
                String.class.equals(c));
    }

    /**
     * @param value String value to be converted.
     * @return Byte representation.
     */
    public Byte convertToByte(String value)
    {
        return convert(Byte.class,value);
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either Character or String.
     * @return String or Character representation.
     */
    public <T> T convertToStringOrChar(String value, Class<T> classType)
    {
        return convert(classType,value);
    }

    /**
     * @param value     String value to be converted.
     * @param classType Class type to be returned  - either BigDecimal or BigInteger.
     * @return Either BigDecimal or BigInteger representaion.
     */
    public <T> T convertToBig(String value, Class<T> classType)
    {
        return convert(classType,value);
    }

    /**
     * @param value     String value to be converted.
     * @param classType Numeric Class type.
     * @return Numeric representation.
     */
    public <T> T convertToNumber(String value, Class<T> classType)
    {
        return convert(classType,value);
    }

    /**
     * @param value String value to be converted.
     * @return Boolean representation.
     */
    public Boolean convertToBoolean(String value)
    {
        return convert(Boolean.class,value);
    }

    private<T> boolean isClass(Class<T> c, Predicate<Class<T>>predicate)
    {
        Validate.notNull(c,"Class is required.");
        return predicate.test(c);
    }

    private <T>T convert(Class<T>classType, String value)
    {
        validateClassIsPresent(value, classType);
        return converterFunctionsMappings.containsKey(classType) ?
                (T) converterFunctionsMappings.get(classType).apply(value) :
                (T) value;
    }

    private <T> void validateClassIsPresent(String value, Class<T> classType)
    {
        Validate.notNull(value,"Class is required.");
        Validate.notNull(classType,"Class type is required.");
    }
}
