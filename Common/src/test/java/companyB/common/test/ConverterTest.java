package companyB.common.test;

import companyB.common.conversion.Converter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@Test(groups = {"unit","converter","utils","common"})
public class ConverterTest
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
            assertThat(ret,is(not(nullValue())));
            assertThat(converter.isNumberType(ret.getClass()),is(true));
            if (Double.class.equals(c) || double.class.equals(c))
                assertThat(String.valueOf(Double.parseDouble(numberString)), is(equalTo(String.valueOf(ret))));
            else
                assertThat(numberString, is(equalTo(String.valueOf(ret))));
        }
    }


    public void byteConversion()
    {
        String byteValues = "This needs to be converted into bytes";
        for (byte b : byteValues.getBytes())
        {
            Object ret = converter.convertToByte(String.valueOf(b));
            assertThat(ret,is(not(nullValue())));
            assertThat(converter.isByte(ret.getClass()),is(true));
            assertThat(b, is(equalTo(ret)));
        }
    }


    public void isByte()
    {
        for (Class c : new Class[]{Byte.class, byte.class}) assertThat(converter.isByte(c),is(true));
    }

    public void isByteFalse()
    {
        assertFalse(converter.isByte(int.class));
    }

    public void isBoolean()
    {
        for(Class c : new Class[]{Boolean.class,boolean.class}) assertThat(converter.isBoolean(c),is(true));
    }

    public void isBooleanFalse()
    {
        assertThat(converter.isBoolean(int.class),is(false));
    }


    public void invalidSupportedType()
    {
        assertThat(converter.isSupported(this.getClass()),is(false));
    }


    public void validSupportedTypes()
    {
        for (Class c : Converter.supportedClasses) assertThat(converter.isSupported(c),is(true));
    }


    public void getStringOrChar()
    {
        String charValue = "c";
        for (Class c : new Class[]{String.class, char.class, Character.class})
        {
            Object ret = converter.convertToStringOrChar(charValue, c);
            assertThat(ret,is(not(nullValue())));
            assertThat(converter.isCharOrString(ret.getClass()),is(true));
            assertThat(charValue, is(equalTo(String.valueOf(ret))));
        }
    }


    public void isStringOrChar()
    {
        for (Class c : new Class[]{String.class, char.class, Character.class}) assertThat(converter.isCharOrString(c),is(true));
    }

    public void isStringOrCharFalse()
    {
        assertThat(converter.isCharOrString(int.class),is(false));
    }


    public void isBigType()
    {
        for (Class c : new Class[]{BigDecimal.class, BigInteger.class}) assertThat(converter.isBigType(c),is(true));
    }
    public void isBigTypeFalse()
    {
        assertThat(converter.isBigType(int.class),is(false));
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
        assertThat(converter.isBigType(ret.getClass()),is(true));
        assertThat(big, is(equalTo(String.valueOf(ret))));

    }

    private void testForBoolean(boolean testForTrue)
    {
        final List<String> values = (testForTrue) ? Converter.trueValues : Converter.falseValues;
        values.forEach((value)->
        {
            final Object ret = converter.convertToBoolean(value);
            assertThat(ret,is(not(nullValue())));
            assertThat(converter.isBoolean(ret.getClass()),is(true));
            assertThat(testForTrue, is(equalTo(ret)));
        });
    }

}
