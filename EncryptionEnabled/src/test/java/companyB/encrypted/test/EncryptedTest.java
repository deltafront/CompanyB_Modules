package companyB.encrypted.test;

import companyB.encrypted.Encrypted;
import companyB.encrypted.EncryptedDecorator;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

@Test(groups = {"unit","encrypted","encrypted.enabled"})
public class EncryptedTest
{

    public void doTestClass()
    {
        TestObject testObject = EncryptedDecorator.decorate(TestObject.class);
        verifyTestObject(testObject);
    }
    public void doTestInstance()
    {
        TestObject testObject = EncryptedDecorator.decorate(new TestObject());
        verifyTestObject(testObject);
    }
    private void verifyTestObject(TestObject testObject)
    {
        Field[]fields = testObject.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                String name = field.getName();
                String value = String.valueOf(field.get(testObject));
                assertNotNull(value);
                assertFalse(field.getAnnotation(Encrypted.class) != null && value.equals(name));
                System.out.println(value);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }
    }
}
