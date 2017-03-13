package companyB.common.test;

import companyB.common.test.objects.TestObject;
import companyB.common.test.objects.test;
import companyB.common.utils.FactoryUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

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
        validateNotNull(loaded);
        validateTrue(loaded instanceof test);
    }

    
    public void loadObjectFalse()
    {
        final Object loaded = factoryUtils.loadObject("java.lang.Foo");
        validateNull(loaded);
    }

    
    public void notStatic()
    {
        twoInstanceTests(false,this::validateNotSame);
    }

    
    public void isStaticInitial()
    {
        twoInstanceTests(true, this::validateNotSame);
    }

    
    public void isStaticAfter()
    {
        testThreeInstances(false,this::validateNotSame,this::validateNotSame,this::validateNotSame);
    }

    
    public void isStaticOneNotTwoIsThree()
    {
        testThreeInstances(true,this::validateNotSame,this::validateSame,this::validateNotSame);
    }

    public void instantiateNoArgs()
    {
        final Object[]args = new Object[]{};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        validateNotNull(testObject);
        validateEquality("foo",testObject.stringValue);
        validateEquality(false,testObject.booleanValue);
        validateEquality(42,testObject.intValue);
    }
    
    public void instantiateOneArgsTrue()
    {
        instantiateOneArg(new Object[]{17,"this",true});
    }
    
    @SuppressWarnings({"UnnecessaryBoxing", "BooleanConstructorCall"})
    public void instantiateOneArgsTrueBoxed()
    {
        instantiateOneArg(new Object[]{new Integer(17),"this",new Boolean(true)});
    }

    
    public void instantiateThreeArgsTrue()
    {
        instantiateThreeArgs(new Object[]{17,"this",true},false);
    }

    @SuppressWarnings("UnnecessaryBoxing")
    public void instantiateThreeArgsTrueBoxed()
    {
       instantiateThreeArgs(new Object[]{new Integer(17),"this",new Boolean(true)},false);
    }

    public void instantiateThreeArgsWrongOrder()
    {
        instantiateThreeArgs(new Object[]{"this",17,true},true);
    }

    private void instantiateThreeArgs(Object[]args, Boolean nullExpected)
    {
        final TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        if(!nullExpected)
        {
            validateNotNull(testObject);
            validateEquality("this",testObject.stringValue);
            validateEquality(true,testObject.booleanValue);
            validateEquality(17,testObject.intValue);
        }
        else
        {
            validateNull(testObject);
        }
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
    private void instantiateOneArg(Object[]args)
    {
        Arrays.asList(args).forEach((arg)->{
            final TestObject testObject = factoryUtils.getInstance(TestObject.class, new Object[]{arg});
            verifyTestInstance(testObject, new Object[]{arg});
        });
    }

    private void twoInstanceTests(Boolean firstStatic, BiConsumer<Object,Object>validationMethod)
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, firstStatic);
        validateTestInstance(loaded_1);

        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        validateTestInstance(loaded_2);

        validationMethod.accept(loaded_1,loaded_2);
    }

    private void testThreeInstances(Boolean firstStatic,BiConsumer<Object,Object>firstValidation,
                                    BiConsumer<Object,Object>secondValidation, BiConsumer<Object,Object>thirdValidation)
    {
        final Object loaded_1 = factoryUtils.loadObject(valid_class_name, firstStatic);
        validateTestInstance(loaded_1);

        final Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        validateTestInstance(loaded_2);

        firstValidation.accept(loaded_1,loaded_2);

        final Object loaded_3 = factoryUtils.loadObject(valid_class_name, true);
        validateTestInstance(loaded_3);

        secondValidation.accept(loaded_1,loaded_3);
        thirdValidation.accept(loaded_2,loaded_3);
    }



    public void instantiateInvalidArgs()
    {
        final Object[]args = {Long.MAX_VALUE};
        final TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertThat(testObject,is(nullValue()));
    }
    private void validateTestInstance(Object instance)
    {
        validateNotNull(instance);
        validateTrue(instance instanceof  test);
    }
}