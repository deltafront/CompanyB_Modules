package companyB.context.test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TestBase
{
    public void verifyTestInstance(Object testObject, Object[]args)
    {
        assertNotNull(testObject);
        for(Object arg : args)
        {
            try
            {
                Field[]fields = testObject.getClass().getFields();
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
    protected void validateEquality(Object expected, Object actual)
    {
        assertThat(expected,is(equalTo(actual)));
    }
    void validateNull(Object instance)
    {
        assertThat(instance,is(nullValue()));
    }
    protected void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
    protected void validateTrue(Boolean condition)
    {
        assertThat(condition,is(true));
    }
    protected void validateFalse(Boolean condition)
    {
        assertThat(condition,is(false));
    }
}
