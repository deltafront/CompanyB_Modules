package companyB.context.test;

import java.util.Arrays;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TestBase
{
    void verifyTestInstance(Object testObject, Object[]args)
    {
        assertNotNull(testObject);
        Arrays.asList(args).forEach((arg)->
        {
            final Long count = Arrays.asList(testObject.getClass().getDeclaredFields()).stream().filter((field) ->
            {
                try
                {
                    return arg == field.get(testObject) || arg.equals(field.get(testObject));
                }
                catch (IllegalAccessException e)
                {
                    return false;
                }
            }).count();
            validateTrue(count > 0);
        });


    }
    void validateEquality(Object expected, Object actual)
    {
        assertThat(expected,is(equalTo(actual)));
    }
    void validateNull(Object instance)
    {
        assertThat(instance,is(nullValue()));
    }
    void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
    void validateTrue(Boolean condition)
    {
        assertThat(condition,is(true));
    }
    void validateFalse(Boolean condition)
    {
        assertThat(condition,is(false));
    }
}
