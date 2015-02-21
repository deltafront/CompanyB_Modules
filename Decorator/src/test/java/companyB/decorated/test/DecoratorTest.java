package companyB.decorated.test;

import companyB.decorated.BeanDecorator;
import companyB.decorated.UnsupportedTypeException;
import companyB.decorated.test.testclasses.AbstractTestClass;
import companyB.decorated.test.testclasses.StaticMemberTestClass;
import companyB.decorated.test.testclasses.TestClass;
import companyB.decorated.test.testclasses.UnsupportedTypeTestClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

/**
 * Created by chburrell on 2/16/15.
 */
public class DecoratorTest
{
    private TestClass testClass;

    @Test
    public void stringValNoDefault() throws UnsupportedTypeException
    {
        String value = "value";
        set("stringValNoDefault",value);
        assertEquals(value,testClass.stringValNoDefault);
    }
    @Test
    public void stringValCanNotSet() throws UnsupportedTypeException
    {
        String value = "value";
        set("canNotSet",value);
        assertEquals("Can not set",testClass.canNotSet);
    }
    @Test
    public void stringValDefault() throws UnsupportedTypeException
    {
        set("stringValDefault","");
        assertEquals("this",testClass.stringValDefault);
    }
    @Test
    public void stringValDefaultNotFound() throws UnsupportedTypeException
    {
        set("stringValDefault","this");
        assertEquals("Found!",testClass.notFoundField);
    }

    @Test
    public void stringValAltName() throws UnsupportedTypeException
    {
        String value = "value";
        set("stringAlt",value);
        assertEquals(value,testClass.stringValAltName);
    }
    @Test
    public void stringValAltNameDefault() throws UnsupportedTypeException
    {
        set("stringAltNameDefault","");
        assertEquals("alt",testClass.stringValAltNameDefault);
    }

    @Test
    public void longVal() throws UnsupportedTypeException
    {
        Long value = 42L;
        set("longVal", String.valueOf(value));
        assertEquals(value, testClass.longVal);
    }

    @Test
    public void shortVal() throws UnsupportedTypeException
    {
        Short value = 42;
        set("shortVal",String.valueOf(value));
        assertEquals(value, testClass.shortVal);
    }

    @Test
    public void doubleVal() throws UnsupportedTypeException
    {
        Double value = 42.00;
        set("doubleVal",String.valueOf(value));
        assertEquals(value,testClass.doubleVal);
    }

    @Test
    public void intVal() throws UnsupportedTypeException
    {
        Integer value = 42;
        set("intVal",String.valueOf(value));
        assertEquals(value,testClass.intVal);
    }

    @Test
    public void booleanVal() throws UnsupportedTypeException
    {
        Boolean value = true;
        set("boolVal",String.valueOf(value));
        assertEquals(value,testClass.boolVal);
    }

    @Test
    public void charVal() throws UnsupportedTypeException
    {
        Character value = 'a';
        set("charVal",String.valueOf(value));
        assertEquals(value,testClass.charVal);
    }
    @Test
    public void byteVal() throws UnsupportedTypeException
    {
        Byte value = 0101;
        set("byteVal",String.valueOf(value));
        assertEquals(value,testClass.byteVal);
    }
    @Test
    public void bigIntVal() throws UnsupportedTypeException
    {
        BigInteger value = new BigInteger("10101");
        set("bigIntVal",String.valueOf(value));
        assertEquals(value,testClass.bigIntVal);
    }
    @Test
    public void bigDecimalVal() throws UnsupportedTypeException
    {
        BigDecimal value = new BigDecimal("10101.0001");
        set("bigDecimalVal",String.valueOf(value));
        assertEquals(value,testClass.bigDecimalVal);
    }
    @Test(expected = UnsupportedTypeException.class)
    public void unsupportedType() throws UnsupportedTypeException
    {
        String value = "1,2,3";
        Properties properties = new Properties();
        properties.setProperty("stringListVal", value);
        BeanDecorator.decorate(UnsupportedTypeTestClass.class,properties);
        fail("As of now, Lists are not supported.");
    }
    @Test
    public void innerClass() throws UnsupportedTypeException
    {
        String value = "value";
        Properties properties = new Properties();
        properties.setProperty("stringVal",value);
        TestClass.PublicInnerClass publicInnerClass = BeanDecorator.decorate(TestClass.PublicInnerClass.class,properties);
        assertEquals(value, publicInnerClass.stringVal);
    }
    @Test
    public void abstractClass() throws UnsupportedTypeException
    {
        String value = "value";
        Properties properties = new Properties();
        properties.setProperty("stringVal",value);
        AbstractTestClass abstractTestClass = BeanDecorator.decorate(AbstractTestClass.class,properties);
        assertNull(abstractTestClass);
    }

    @Test
    public void staticMembers() throws UnsupportedTypeException
    {
        String value = "value";
        Properties properties = new Properties();
        properties.setProperty("stringVal",value);
        StaticMemberTestClass staticMemberTestClass = BeanDecorator.decorate(StaticMemberTestClass.class,properties);
        assertNull(StaticMemberTestClass.stringVal);
    }



    private void set(String key, String value) throws UnsupportedTypeException
    {
        Properties properties = new Properties();
        properties.setProperty(key,value);
        testClass = BeanDecorator.decorate(TestClass.class,properties);
    }

}
