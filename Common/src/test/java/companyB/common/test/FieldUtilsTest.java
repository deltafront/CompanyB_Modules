package companyB.common.test;

import companyB.common.test.annotations.Bar;
import companyB.common.test.annotations.Foo;
import companyB.common.utils.FieldUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

@Test(groups = {"unit","common","field.utils"})
public class FieldUtilsTest
{

    FieldUtils fieldUtils;
    testclass testclass;

    @BeforeMethod
    public void before()
    {
        fieldUtils = new FieldUtils();
        testclass = new testclass();
    }

    public void testGetValidField()
    {
        assertNotNull(fieldUtils.getField("testfield",testclass));
    }

    public void testGetInvalidField()
    {
        assertNull(fieldUtils.getField("foo", testclass));
    }

    public void testGetValidAnnotation()
    {
        Field field = fieldUtils.getField("testfield",testclass);
        assertNotNull(fieldUtils.getField("testfield",testclass));
        Foo foo = fieldUtils.getAnnotation(Foo.class,field);
        assertNotNull(foo);
    }

    public void testGetInvalidAnnotation()
    {
        Field field = fieldUtils.getField("testfield",testclass);
        assertNotNull(fieldUtils.getField("testfield",testclass));
        assertNull(fieldUtils.getAnnotation(Bar.class, field));
    }

    public void testSetValidField()
    {
        Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        Object fromClass = testclass.getTestfield();
        assertNotNull(fromClass);
        assertEquals(value,fromClass);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testSetInvalidField()
    {
        Object value = "foo";
        fieldUtils.setField("foo",testclass,value);
        fail("Null pointer exception - field 'foo' does not exist.");
    }

    public void testGetValidFieldValue()
    {
        Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        Object fromUtils = fieldUtils.getFieldValue("testfield",testclass);
        assertNotNull(fromUtils);
        assertEquals(value,fromUtils);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetInValidFieldValue()
    {
        Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        fieldUtils.getFieldValue("foo", testclass);
        fail("Null pointer exception - field 'foo' does not exist.");
    }

    public void testGetFields()
    {
        Field[]fields = fieldUtils.getFields(testclass);
        assertNotNull(fields);
    }

    public static class testclass
    {
        @SuppressWarnings("all")
        @Foo
        private Object testfield;

        public Object getTestfield()
        {
            return testfield;
        }
    }
}
