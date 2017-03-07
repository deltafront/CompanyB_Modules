package companyB.encrypted.test;

import companyB.encrypted.Encrypted;
import companyB.encrypted.EncryptedDecorator;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

@Test(groups = {"unit","encrypted","encrypted.enabled"})
public class EncryptedTest extends TestBase
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
                validateNotNull(value);
                final BiConsumer<Object,Object> consumer = (null==field.getAnnotation(Encrypted.class)) ?
                        this::validateEquality : this::validateNonEquality;
                consumer.accept(name,value);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }
    }
}
