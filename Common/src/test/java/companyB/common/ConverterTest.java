package companyB.common;

import companyB.common.conversion.Converter;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@Test(groups = {"unit","converter","common"})
public class ConverterTest
{
    public void testTrueBoolean()
    {
        testForBoolean(true);
    }

    public void testFalseBoolean()
    {
        testForBoolean(false);
    }


    public void testNumberConversions()
    {
        String numberString = "42";
        for (Class c : Converter.numberClasses)
        {
            Object ret = Converter.convertToNumber(numberString, c);
            assertNotNull(ret);
            assertTrue(Converter.isNumberType(ret.getClass()));
            if (Double.class.equals(c) || double.class.equals(c))
            {
                assertEquals(String.valueOf(Double.parseDouble(numberString)), String.valueOf(ret));
            }
            else
            {
                assertEquals(numberString, String.valueOf(ret));
            }
        }
    }


    public void testByteConversion()
    {
        String byteValues = "This needs to be converted into bytes";
        for (byte b : byteValues.getBytes())
        {
            Object ret = Converter.convertToByte(String.valueOf(b));
            assertNotNull(ret);
            assertTrue(Converter.isByte(ret.getClass()));
            assertEquals(b, ret);
        }
    }


    public void testIsByte()
    {
        for (Class c : new Class[]{Byte.class, byte.class})
        {
            assertTrue(Converter.isByte(c));
        }
    }


    public void testInvalidSupportedType()
    {
        assertFalse(Converter.isSupported(this.getClass()));
    }


    public void testValidSupportedTypes()
    {
        for (Class c : Converter.supportedClasses)
        {
            assertTrue(Converter.isSupported(c));
        }
    }


    public void testGetStringOrChar()
    {
        String charValue = "c";
        for (Class c : new Class[]{String.class, char.class, Character.class})
        {
            Object ret = Converter.convertToStringOrChar(charValue, c);
            assertNotNull(ret);
            assertTrue(Converter.isCharOrString(ret.getClass()));
            assertEquals(charValue, String.valueOf(ret));
        }
    }


    public void testIsStringOrChar()
    {
        for (Class c : new Class[]{String.class, char.class, Character.class})
        {
            assertTrue(Converter.isCharOrString(c));
        }
    }


    public void testIsBigType()
    {
        for (Class c : new Class[]{BigDecimal.class, BigInteger.class})
        {
            assertTrue(Converter.isBigType(c));
        }
    }


    public void testConvertToBigDecimal()
    {
        testForBig(BigDecimal.class);
    }


    public void testConvertToBigInteger()
    {
        testForBig(BigInteger.class);
    }

    private void testForBig(Class c)
    {
        boolean isBigDecimal = BigDecimal.class.equals(c);
        String big = isBigDecimal ? "42.0" : "42";
        Object ret = Converter.convertToBig(big, c);
        assertTrue(Converter.isBigType(ret.getClass()));
        assertEquals(big, String.valueOf(ret));

    }

    private void testForBoolean(boolean testForTrue)
    {
        List<String> values = (testForTrue) ? Converter.trueValues : Converter.falseValues;
        for (String value : values)
        {
            Object ret = Converter.convertToBoolean(value);
            assertNotNull(ret);
            assertTrue(Converter.isBoolean(ret.getClass()));
            assertEquals(testForTrue, ret);
        }
    }

}
