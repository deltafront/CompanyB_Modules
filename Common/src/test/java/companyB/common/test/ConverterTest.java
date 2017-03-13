package companyB.common.test;

import companyB.common.conversion.Converter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Test(groups = {"unit","converter","utils","common"})
public class ConverterTest extends TestBase
{
    private Converter converter;

    @BeforeMethod
    public void before()
    {
        converter = new Converter();
    }
    public void trueBoolean()
    {
        testForBoolean(true);
    }

    public void falseBoolean()
    {
        testForBoolean(false);
    }


    public void numberConversions()
    {
        String numberString = "42";
        for (Class c : Converter.numberClasses)
        {
            Object ret = converter.convertToNumber(numberString, c);
            validateNotNull(ret);
            validateTrue(converter.isNumberType(ret.getClass()));
           if (Double.class.equals(c) || double.class.equals(c))
               validateEquality(String.valueOf(Double.parseDouble(numberString)),String.valueOf(ret));
            else
                validateEquality(numberString, String.valueOf(ret));
        }
    }


    public void byteConversion()
    {
        String byteValues = "This needs to be converted into bytes";
        for (byte b : byteValues.getBytes())
        {
            Object ret = converter.convertToByte(String.valueOf(b));
            validateNotNull(ret);
            validateTrue(converter.isByte(ret.getClass()));
            validateEquality(b, ret);
        }
    }


    public void isByte()
    {
        for (Class c : new Class[]{Byte.class, byte.class}) validateTrue(converter.isByte(c));
    }

    public void isByteFalse()
    {
        validateFalse(converter.isByte(int.class));
    }

    public void isBoolean()
    {
        for(Class c : new Class[]{Boolean.class,boolean.class}) validateTrue(converter.isBoolean(c));
    }

    public void isBooleanFalse()
    {
        validateFalse(converter.isBoolean(int.class));
    }


    public void invalidSupportedType()
    {
        validateFalse(converter.isSupported(this.getClass()));
    }


    public void validSupportedTypes()
    {
        for (Class c : Converter.supportedClasses) validateTrue(converter.isSupported(c));
    }


    public void getStringOrChar()
    {
        String charValue = "c";
        for (Class c : new Class[]{String.class, char.class, Character.class})
        {
            Object ret = converter.convertToStringOrChar(charValue, c);
            validateNotNull(ret);
            validateTrue(converter.isCharOrString(ret.getClass()));
            validateEquality(charValue,String.valueOf(ret));
        }
    }


    public void isStringOrChar()
    {
        for (Class c : new Class[]{String.class, char.class, Character.class}) validateTrue(converter.isCharOrString(c));
    }

    public void isStringOrCharFalse()
    {
        validateFalse(converter.isCharOrString(int.class));
    }


    public void isBigType()
    {
        for (Class c : new Class[]{BigDecimal.class, BigInteger.class}) validateTrue(converter.isBigType(c));
    }
    public void isBigTypeFalse()
    {
        validateFalse(converter.isBigType(int.class));
    }


    public void convertToBigDecimal()
    {
        testForBig(BigDecimal.class);
    }


    public void convertToBigInteger()
    {
        testForBig(BigInteger.class);
    }

    private void testForBig(Class c)
    {
        boolean isBigDecimal = BigDecimal.class.equals(c);
        final String big = isBigDecimal ? "42.0" : "42";
        final Object ret = converter.convertToBig(big, c);
        validateTrue(converter.isBigType(ret.getClass()));
        validateEquality(big, ret.toString());

    }

    private void testForBoolean(boolean testForTrue)
    {
        final List<String> values = (testForTrue) ? Converter.trueValues : Converter.falseValues;
        values.forEach((value)->
        {
            final Object ret = converter.convertToBoolean(value);
            validateNotNull(ret);
            validateTrue(converter.isBoolean(ret.getClass()));
            validateEquality(testForTrue,ret);
        });
    }

}
