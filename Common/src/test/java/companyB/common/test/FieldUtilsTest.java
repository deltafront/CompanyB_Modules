package companyB.common.test;

import companyB.common.test.annotations.Bar;
import companyB.common.test.annotations.Foo;
import companyB.common.utils.FieldUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;

@Test(groups = {"unit","common","utils","field.utils"})
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
        assertThat(fieldUtils.getField("testfield",testclass),is(not(nullValue())));
    }

    public void getInvalidField()
    {
        assertThat(fieldUtils.getField("foo", testclass),is(nullValue()));
    }

    public void getValidAnnotation()
    {
        final Field field = fieldUtils.getField("testfield",testclass);
        assertThat(fieldUtils.getField("testfield",testclass),is(not(nullValue())));
        final Foo foo = fieldUtils.getAnnotation(Foo.class,field);
        assertThat(foo,is(not(nullValue())));
    }

    public void getInvalidAnnotation()
    {
        final Field field = fieldUtils.getField("testfield",testclass);
        assertThat(fieldUtils.getField("testfield",testclass),is(not(nullValue())));
        assertThat(fieldUtils.getAnnotation(Bar.class, field),is(nullValue()));
    }

    public void setValidField()
    {
        final Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        final Object fromClass = testclass.getTestfield();
        assertThat(fromClass,is((not(nullValue()))));
        assertThat(value,is(equalTo(fromClass)));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setInvalidField()
    {
        final Object value = "foo";
        fieldUtils.setField("foo",testclass,value);
        fail("Null pointer exception - field 'foo' does not exist.");
    }

    public void getValidFieldValue()
    {
        final Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        final Object fromUtils = fieldUtils.getFieldValue("testfield",testclass);
        assertThat(fromUtils,is(not(nullValue())));
        assertThat(value,is(equalTo(fromUtils)));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getInValidFieldValue()
    {
        final Object value = "foo";
        fieldUtils.setField("testfield",testclass,value);
        fieldUtils.getFieldValue("foo", testclass);
        fail("Null pointer exception - field 'foo' does not exist.");
    }

    public void getFields()
    {
        final Field[]fields = fieldUtils.getFields(testclass);
        assertThat(fields,is(not(nullValue())));
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
