package companyB.context.test;

import companyB.context.ClassArgsContainer;
import companyB.context.DefaultIApplicationContext;
import companyB.context.I_ApplicationContext;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"UnnecessaryBoxing", "BooleanConstructorCall"})
@Test(groups = {"unit","default.application.context","context.enabled"})
public class DefaultIApplicationContextTest extends TestBase
{
    public void twoInstances()
    {
        I_ApplicationContext one = new DefaultIApplicationContext();
        I_ApplicationContext two = new DefaultIApplicationContext();

        try
        {
            String key = "this";
            String value = "that";
            assertTrue(one.associate(key,value));
            assertFalse(two.associate(key, value));
            assertEquals(value,two.get(key));
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
                assertTrue(applicationContext.associate(key, value));
                assertFalse(applicationContext.associate(key, value2));
                assertEquals(value, applicationContext.get(key));
                Set<String> keys = applicationContext.getKeys();
                assertEquals(i + 1, keys.size());
            }
        }
        finally
        {
            applicationContext.clear();
        }
    }


    @SuppressWarnings("UnusedAssignment")
    public void getInstance()
    {
        I_ApplicationContext applicationContext = new DefaultIApplicationContext();
        try
        {
            Object[] no_args = new Object[0];
            Object[]null_args = new Object[]{"this",17,true};
            Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
            Object[]args_a = new Object[]{true,"this"};
            Object[]args_b = new Object[]{1,"this"};
            Object[]args_c = new Object[]{1,true};
            Object[]args_d = new Object[]{17,"this",true};

            TestObject null_to = applicationContext.getInstance(TestObject.class,null_args,"null");
            TestObject no_to = applicationContext.getInstance(TestObject.class,no_args,"no");
            no_to = applicationContext.getInstance(TestObject.class,no_args,"no");
            TestObject testObject_a = applicationContext.getInstance(TestObject.class,args_a,"a");
            TestObject testObject_b = applicationContext.getInstance(TestObject.class,args_b,"b");
            TestObject testObject_c = applicationContext.getInstance(TestObject.class,args_c,"c");
            TestObject testObject_d = applicationContext.getInstance(TestObject.class,args_d,"d");
            TestObject args_to = applicationContext.getInstance(TestObject.class, args,"args");

            assertNull(null_to);
            assertNotNull(no_to);
            assertNotNull(testObject_a);
            assertNotNull(testObject_b);
            assertNotNull(testObject_c);
            assertNotNull(testObject_d);
            assertNotNull(args_to);

            verifyTestInstance(testObject_a,args_a);
            verifyTestInstance(testObject_b,args_b);
            verifyTestInstance(testObject_c,args_c);
            verifyTestInstance(testObject_d,args_d);
            verifyTestInstance(args_to,args);

            testObject_a = applicationContext.get("a");
            testObject_b = applicationContext.get("b");
            testObject_c = applicationContext.get("c");
            testObject_d = applicationContext.get("d");
            no_to = applicationContext.get("no");
            null_to = applicationContext.get("null");
            args_to = applicationContext.get("args");

            assertNull(null_to);
            assertNotNull(no_to);
            assertNotNull(testObject_a);
            assertNotNull(testObject_b);
            assertNotNull(testObject_c);
            assertNotNull(testObject_d);
            assertNotNull(args_to);

            verifyTestInstance(testObject_a,args_a);
            verifyTestInstance(testObject_b,args_b);
            verifyTestInstance(testObject_c,args_c);
            verifyTestInstance(testObject_d,args_d);
            verifyTestInstance(args_to,args);
        }
        finally
        {
            applicationContext.clear();
        }
    }

    public void withContainers() throws ClassNotFoundException
    {
        String fqcn = TestObject.class.getCanonicalName();

        Object[] no_args = new Object[0];
        Object[]null_args = new Object[]{"this",17,true};
        Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
        Object[]args_a = new Object[]{true,"this"};
        Object[]args_b = new Object[]{1,"this"};
        Object[]args_c = new Object[]{1,true};
        Object[]args_d = new Object[]{17,"this",true};

        ClassArgsContainer no_args_con = new ClassArgsContainer(fqcn,no_args,"no");
        ClassArgsContainer null_args_con = new ClassArgsContainer(fqcn,null_args,"null");
        ClassArgsContainer args_con = new ClassArgsContainer(fqcn,args,"args");
        ClassArgsContainer args_a_con = new ClassArgsContainer(fqcn,args_a,"a");
        ClassArgsContainer args_b_con = new ClassArgsContainer(fqcn,args_b,"b");
        ClassArgsContainer args_c_con = new ClassArgsContainer(fqcn,args_c,"c");
        ClassArgsContainer args_d_con = new ClassArgsContainer(fqcn,args_d,"d");

        ClassArgsContainer[]containers = new ClassArgsContainer[]
        {no_args_con,null_args_con,args_con,args_a_con,args_b_con,args_c_con,args_d_con};
        List<ClassArgsContainer> containerList = new LinkedList<>();
        Collections.addAll(containerList,containers);

        I_ApplicationContext applicationContext = new DefaultIApplicationContext(containerList);
        try
        {
            TestObject testObject_a = applicationContext.get("a");
            TestObject testObject_b = applicationContext.get("b");
            TestObject testObject_c = applicationContext.get("c");
            TestObject testObject_d = applicationContext.get("d");
            TestObject no_to = applicationContext.get("no");
            TestObject null_to = applicationContext.get("null");
            TestObject args_to = applicationContext.get("args");

            assertNull(null_to);
            assertNotNull(no_to);
            assertNotNull(testObject_a);
            assertNotNull(testObject_b);
            assertNotNull(testObject_c);
            assertNotNull(testObject_d);
            assertNotNull(args_to);

            verifyTestInstance(testObject_a,args_a);
            verifyTestInstance(testObject_b,args_b);
            verifyTestInstance(testObject_c,args_c);
            verifyTestInstance(testObject_d,args_d);
            verifyTestInstance(args_to,args);
        }
        finally
        {
            applicationContext.clear();
        }
    }
}
