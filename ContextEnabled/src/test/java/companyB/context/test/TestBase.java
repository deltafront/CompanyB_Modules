package companyB.context.test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class TestBase
{
    public void verifyTestInstance(TestObject testObject, Object[]args)
    {
        assertNotNull(testObject);
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
                assertTrue(String.format("Did not find value %s in test object!",arg),found);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

    }
}
