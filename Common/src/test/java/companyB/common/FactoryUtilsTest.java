package companyB.common;

import companyB.common.objects.test;
import companyB.common.utils.FactoryUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryUtilsTest
{

    private String valid_class_name = test.class.getCanonicalName();

    @Test
    public void testLoadObjectTrue()
    {
        Object loaded = FactoryUtils.loadObject(valid_class_name);
        assertNotNull(loaded);
        assertTrue(loaded instanceof test);
    }

    @Test
    public void testLoadObjectFalse()
    {
        Object loaded = FactoryUtils.loadObject("java.lang.Foo");
        assertNull(loaded);
    }

    @Test
    public void notStatic()
    {
        Object loaded_1 = FactoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = FactoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
    }

    @Test
    public void isStaticInitial()
    {
        Object loaded_1 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() == loaded_2.hashCode());
    }

    @Test
    public void isStaticAfter()
    {
        Object loaded_1 = FactoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
        Object loaded_3 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_3);
        assertTrue(loaded_3 instanceof test);
        assertTrue(loaded_3.hashCode() == loaded_2.hashCode());
        assertTrue(loaded_3.hashCode() != loaded_1.hashCode());
    }

    @Test
    public void isStaticOneNotTwoIsThree()
    {
        Object loaded_1 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_1);
        assertTrue(loaded_1 instanceof test);
        Object loaded_2 = FactoryUtils.loadObject(valid_class_name, false);
        assertNotNull(loaded_2);
        assertTrue(loaded_2 instanceof test);
        assertTrue(loaded_1.hashCode() != loaded_2.hashCode());
        Object loaded_3 = FactoryUtils.loadObject(valid_class_name, true);
        assertNotNull(loaded_3);
        assertTrue(loaded_3 instanceof test);
        assertTrue(loaded_3.hashCode() == loaded_1.hashCode());
        assertTrue(loaded_3.hashCode() != loaded_2.hashCode());
    }
}