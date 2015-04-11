package companyB.common;

import companyB.common.objects.TestObject;
import companyB.common.objects.test;
import companyB.common.utils.FactoryUtils;
import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.fail;

@Test(groups = {"unit","factory.utils","common"})
public class FactoryUtilsTest
{
    private FactoryUtils factoryUtils;
    @BeforeMethod
    public void before()
    {
        factoryUtils = new FactoryUtils();
    }

    private String valid_class_name = test.class.getCanonicalName();
    private Class[] primitives = new Class[]
            {
                    int.class,long.class,short.class,double.class,byte.class,char.class,boolean.class,
                    Integer.class, Long.class, Short.class,Double.class,Byte.class,Character.class,Boolean.class
            };
    private Class[] pClasses = new Class[]{int.class,long.class,short.class,double.class,byte.class,char.class,boolean.class};
    private Class[] bClasses = new Class[]{Integer.class, Long.class, Short.class,Double.class,Byte.class,Character.class,Boolean.class};
    private Object[]objects = new Object[]{Integer.MAX_VALUE,Long.MAX_VALUE,Short.MAX_VALUE,Double.MAX_VALUE,Byte.MAX_VALUE,Character.MAX_VALUE,
            Boolean.TRUE};

    
    public void testLoadObjectTrue()
    {
        Object loaded = factoryUtils.loadObject(valid_class_name);
        assertNotNull(loaded);
        assertTrue(loaded instanceof test);
    }

    
    public void testLoadObjectFalse()
    {
        Object loaded = factoryUtils.loadObject("java.lang.Foo");
        assertNull(loaded);
    }

    
    public void notStatic()
    {
        Object loaded_1 = factoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
    }

    
    public void isStaticInitial()
    {
        Object loaded_1 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() == loaded_2.hashCode());
    }

    
    public void isStaticAfter()
    {
        Object loaded_1 = factoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
        Object loaded_3 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_3);
        assertTrue(loaded_3 instanceof test);
        assertTrue(loaded_3.hashCode() == loaded_2.hashCode());
        assertTrue(loaded_3.hashCode() != loaded_1.hashCode());
    }

    
    public void isStaticOneNotTwoIsThree()
    {
        Object loaded_1 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = factoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
        Object loaded_3 = factoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_3);
        assertTrue(loaded_3 instanceof test);
        assertTrue(loaded_3.hashCode() == loaded_1.hashCode());
        assertTrue(loaded_3.hashCode() != loaded_2.hashCode());
    }
    
    public void instantiateNoArgs()
    {
        Object[]args = new Object[]{};
        TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertNotNull(testObject);
        assertEquals("foo",testObject.stringValue);
        assertEquals(false, testObject.booleanValue);
        assertEquals(42,testObject.intValue);
    }
    
    public void instantiateOneArgsTrue()
    {
        Object[]args = new Object[]{17,"this",true};
        for(Object arg : args)
        {
            TestObject testObject = factoryUtils.getInstance(TestObject.class,new Object[]{arg});
            assertNotNull(testObject);
            try
            {
                Field[]fields = TestObject.class.getFields();
                boolean found = false;
                for(Field field : fields)
                {
                    Object fromField = field.get(testObject);
                    if (arg.equals(fromField) || arg == fromField)
                    {
                        found = true;
                        break;
                    }
                }
                System.out.println(testObject.toString());
                assertTrue(String.format("Did not find value %s in test object!", arg), found);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void instantiateOneArgsTrueBoxed()
    {
        Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
        for(Object arg : args)
        {
            TestObject testObject = factoryUtils.getInstance(TestObject.class,new Object[]{arg});
            verifyTestInstance(testObject,new Object[]{arg});
        }
    }

    
    public void instantiateThreeArgsTrue()
    {
        Object[]args = new Object[]{17,"this",true};
        TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertNotNull(testObject);
        assertEquals("this",testObject.stringValue);
        assertEquals(true,testObject.booleanValue);
        assertEquals(17,testObject.intValue);
    }
    
    public void instantiateThreeArgsWrongOrder()
    {
        Object[]args = new Object[]{"this",17,true};
        TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertNull(testObject);
    }
    
    public void instantiateThreeArgsTrueBoxed()
    {
        Object[]args = new Object[]{new Integer(17),"this",new Boolean(true)};
        TestObject testObject = factoryUtils.getInstance(TestObject.class,args);
        assertNotNull(testObject);
        assertEquals("this",testObject.stringValue);
        assertEquals(true,testObject.booleanValue);
        assertEquals(17,testObject.intValue);
    }

    
    public void instantiateTwoArgsTrue()
    {
        Object[]args_a = new Object[]{true,"this"};
        Object[]args_b = new Object[]{1,"this"};
        Object[]args_c = new Object[]{1,true};

        TestObject testObject_a = factoryUtils.getInstance(TestObject.class,args_a);
        TestObject testObject_b = factoryUtils.getInstance(TestObject.class,args_b);
        TestObject testObject_c = factoryUtils.getInstance(TestObject.class,args_c);

        verifyTestInstance(testObject_a,args_a);
        verifyTestInstance(testObject_b,args_b);
        verifyTestInstance(testObject_c,args_c);
    }

    
    public void withGenericInterface()
    {
        LinkedList<String> list = new LinkedList<>();
        Object[]args = new Object[]{list};
        TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertNotNull(testObject);
        assertEquals(list, testObject.booleanIterable);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void withGenericInterfaceWrongType()
    {
        LinkedList<Integer>list = new LinkedList<>();
        for(int i = 0; i < 100; i++)
        {
            list.add(i);
        }
        Object[]args = new Object[]{list};
        TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertNotNull(testObject);
        Iterable<Boolean>booleanIterable = testObject.booleanIterable;
        for(Boolean fromList : booleanIterable)
        {
            fail("ClassCastException Expected.");
        }
    }

    
    public void instantiateInvalidArgs()
    {
        Object[]args = {Long.MAX_VALUE};
        TestObject testObject = factoryUtils.getInstance(TestObject.class, args);
        assertNull(testObject);
    }
    private void verifyTestInstance(TestObject testObject, Object[]args)
    {
        Assert.assertNotNull(testObject);
        for(Object arg : args)
        {
            try
            {
                Field[]fields = TestObject.class.getFields();
                boolean found = false;
                for(Field field : fields)
                {
                    Object fromField = field.get(testObject);
                    if (arg.equals(fromField) || arg == fromField)
                    {
                        found = true;
                        break;
                    }
                }
                System.out.println(testObject.toString());
                assertTrue(String.format("Did not find value %s in test object!",arg),found);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

    }
}