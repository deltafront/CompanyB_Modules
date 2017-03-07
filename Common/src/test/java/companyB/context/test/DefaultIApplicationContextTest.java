package companyB.context.test;

import companyB.context.ClassArgsContainer;
import companyB.context.DefaultIApplicationContext;
import companyB.context.I_ApplicationContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

@SuppressWarnings({"UnnecessaryBoxing", "BooleanConstructorCall"})
@Test(groups = {"unit","default.application.context","context.enabled"})
public class DefaultIApplicationContextTest extends TestBase
{
    @DataProvider(name = "default")
    public static Object[][] parameters()
    {
        return new Object[][]{
                {new Object[]{"this",17,true},"null"},
                {new Object[]{new Integer(17),"this",new Boolean(true)},"args"},
                {new Object[]{true,"this"},"a"},
                {new Object[]{1,"this"},"b"},
                {new Object[]{1,true},"c"},
                {new Object[]{17,"this",true},"d"}
        };
    }
    public void twoInstances()
    {
        I_ApplicationContext one = new DefaultIApplicationContext();
        I_ApplicationContext two = new DefaultIApplicationContext();

        try
        {
            String key = "this";
            String value = "that";
            validateTrue(one.associate(key,value));
            validateFalse(two.associate(key,value));
            validateEquality(value,two.get(key));
        }

        finally
        {
            one.clear();
            two.clear();
        }
    }


    public void associateAndGet()
    {
        I_ApplicationContext applicationContext = new DefaultIApplicationContext();
        try
        {
            int max = 10;
            for(int i = 0; i< max; i++)
            {
                String key = String.format("key_%d",i);
                String value = "that";
                String value2 = "the other";
                validateTrue(applicationContext.associate(key,value));
                validateFalse(applicationContext.associate(key,value2));
                validateEquality(value,applicationContext.get(key));
                Set<String> keys = applicationContext.getKeys();
                validateEquality(i+1,keys.size());
            }
        }
        finally
        {
            applicationContext.clear();
        }
    }


    @SuppressWarnings("UnusedAssignment")

    @Test(dataProvider = "default")
    public void testGetInstance(Object[]args,String context)
    {
        final I_ApplicationContext applicationContext = new DefaultIApplicationContext();
        try
        {
            getAndVerifyTestObject(applicationContext,context,args);
        }
        finally
        {
            applicationContext.clear();
        }
    }
    @Test(dataProvider = "default")
    public void verifyWithContainers(Object[]args,String context)
    {
        I_ApplicationContext applicationContext = null;
        try
        {
            final String fqcn = TestObject.class.getCanonicalName();
            final ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,args,context);
            final ClassArgsContainer[]containers = new ClassArgsContainer[]{classArgsContainer};
            final List<ClassArgsContainer> containerList = new LinkedList<>();
            Collections.addAll(containerList,containers);
            applicationContext = new DefaultIApplicationContext(containerList);
            getAndVerifyTestObject(applicationContext,context,args);
        }
        catch (ClassNotFoundException e)
        {
            fail(e.getMessage());
        }
        finally
        {
            if(null != applicationContext)
                applicationContext.clear();
        }


    }
    private void getAndVerifyTestObject(I_ApplicationContext applicationContext, String context, Object[]args)
    {
        final TestObject testObject = applicationContext.getInstance(TestObject.class,args,context);
        if(!"null".equals(context))
        {
            verifyInstance(testObject,args);
            verifyInstance(applicationContext.get(context),args);
        }
        else
        {
            validateNull(testObject);
            validateNull(applicationContext.get(context));
        }
    }
    private void verifyInstance(TestObject testObject, Object[] args)
    {
        validateNotNull(testObject);
        verifyTestInstance(testObject,args);
    }
}
