package companyB.common.test;

import companyB.common.test.objects.TestObject;
import companyB.common.test.objects.test;
import companyB.common.utils.FactoryUtils;
import companyB.context.test.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.fail;

@SuppressWarnings("BooleanConstructorCall")
@Test(groups = {"unit","factory.utils","utils","common"})
public class FactoryUtilsTest extends TestBase
{
    private FactoryUtils factoryUtils;
    @BeforeMethod
    public void before()
    {
        factoryUtils = new FactoryUtils();
    }

    private String valid_class_name = test.class.getCanonicalName();
    
    public void loadObjectTrue()
    {
        final Object loaded = factoryUtils.loadObject(valid_class_name);
        assertThat(loaded,is(not(nullValue())));
        assertThat(loaded instanceof test, is(true));
    }

    
    public void loadObjectFalse()
    {
        final Object loaded = factoryUtils.loadObject("java.lang.Foo");
        assertThat(loaded, is(nullValue()));
    }

    
    public void notStatic()
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, false);
        assertThat(loaded_1,is(not(nullValue())));
        assertThat(loaded_1 instanceof test,is(true));
        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        assertThat(loaded_2,is(not(nullValue())));
        assertThat(loaded_2 instanceof test,is(true));
        assertThat(loaded_1.hashCode(),is(not(equalTo(loaded_2.hashCode()))));
    }

    
    public void isStaticInitial()
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_1,is(not(nullValue())));
        assertThat(loaded_1 instanceof test,is(true));
        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_2,is(not(nullValue())));
        assertThat(loaded_2 instanceof test,is(true));
        assertThat(loaded_1.hashCode(),is(equalTo(loaded_2.hashCode())));
    }

    
    public void isStaticAfter()
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, false);
        assertThat(loaded_1,is(not(nullValue())));
        assertThat(loaded_1 instanceof test,is(true));
        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_2,is(not(nullValue())));
        assertThat(loaded_2 instanceof test,is(true));
        assertThat(loaded_1.hashCode(),is(not(equalTo(loaded_2.hashCode()))));
        final Object loaded_3 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_3,is(not(nullValue())));
        assertThat(loaded_3 instanceof test,is(true));
        assertThat(loaded_3.hashCode(),is(equalTo(loaded_2.hashCode())));
        assertThat(loaded_3.hashCode(),is(not(equalTo(loaded_1.hashCode()))));
    }

    
    public void isStaticOneNotTwoIsThree()
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_1,is(not(nullValue())));
        assertThat(loaded_1 instanceof test,is(true));
        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        assertThat(loaded_2,is(not(nullValue())));
        assertThat(loaded_2 instanceof test,is(true));
        assertThat(loaded_1.hashCode(),is(not(equalTo(loaded_2.hashCode()))));
        final Object loaded_3 = factoryUtils.loadObject(valid_class_name, true);
        assertThat(loaded_3,is(not(nullValue())));
        assertThat(loaded_3 instanceof test,is(true));
        assertThat(loaded_3.hashCode(),is(equalTo(loaded_1.hashCode())));
        assertThat(loaded_3.hashCode(),is(not(equalTo(loaded_2.hashCode()))));
    }
    
    public void instantiateNoArgs()
    {
        final Object[]args = new Object[]{};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertThat(testObject,is(not(nullValue())));
        assertThat("foo",is(equalTo(testObject.stringValue)));
        assertThat(false, is(equalTo(testObject.booleanValue)));
        assertThat(42,is(equalTo(testObject.intValue)));
    }
    
    public void instantiateOneArgsTrue()
    {
        final Object[]args = new Object[]{17,"this",true};
        Arrays.asList(args).forEach((arg)->{
            final TestObject testObject = factoryUtils.getInstance(TestObject.class, new Object[]{arg});
            verifyTestInstance(testObject, new Object[]{arg});
        });
    }
    
    @SuppressWarnings({"UnnecessaryBoxing", "BooleanConstructorCall"})
    public void instantiateOneArgsTrueBoxed()
    {
        final Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
        Arrays.asList(args).forEach((arg)->{
            final TestObject testObject = factoryUtils.getInstance(TestObject.class, new Object[]{arg});
            verifyTestInstance(testObject, new Object[]{arg});
        });
    }

    
    public void instantiateThreeArgsTrue()
    {
        final Object[]args = new Object[]{17,"this",true};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertThat(testObject,is(not(nullValue())));
        assertThat("this",is(equalTo(testObject.stringValue)));
        assertThat(true,is(equalTo(testObject.booleanValue)));
        assertThat(17,is(equalTo(testObject.intValue)));
    }
    
    public void instantiateThreeArgsWrongOrder()
    {
        final Object[]args = new Object[]{"this",17,true};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertNull(testObject);
    }
    
    @SuppressWarnings("UnnecessaryBoxing")
    public void instantiateThreeArgsTrueBoxed()
    {
        final Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertThat(testObject,is(not(nullValue())));
        assertThat("this",is(equalTo(testObject.stringValue)));
        assertThat(true,is(equalTo(testObject.booleanValue)));
        assertThat(17,is(equalTo(testObject.intValue)));
    }

    
    public void instantiateTwoArgsTrue()
    {
        final Object[]args_a = new Object[]{true,"this"};
        final Object[]args_b = new Object[]{1,"this"};
        final Object[]args_c = new Object[]{1,true};

        final TestObject testObject_a = factoryUtils.getInstance(TestObject.class,args_a);
        final TestObject testObject_b = factoryUtils.getInstance(TestObject.class,args_b);
        final TestObject testObject_c = factoryUtils.getInstance(TestObject.class,args_c);

        verifyTestInstance(testObject_a,args_a);
        verifyTestInstance(testObject_b,args_b);
        verifyTestInstance(testObject_c,args_c);
    }

    
    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    public void withGenericInterface()
    {
        final LinkedList<String> list = new LinkedList<>();
        final Object[]args = new Object[]{list};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertThat(testObject,is(not(nullValue())));
        assertThat(list,is(equalTo(testObject.booleanIterable)));
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void withGenericInterfaceWrongType()
    {
        final LinkedList<Integer>list = new LinkedList<>();
        IntStream.range(0,100).forEach(list::add);
        final Object[]args = new Object[]{list};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertThat(testObject,is(not(nullValue())));
        final Iterable<Boolean>booleanIterable = testObject.booleanIterable;
        booleanIterable.forEach((ignored)->fail("ClassCastException Expected."));
    }

    
    public void instantiateInvalidArgs()
    {
        final Object[]args = {Long.MAX_VALUE};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertThat(testObject,is(nullValue()));
    }
}