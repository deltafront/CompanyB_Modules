package companyB.common.test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TestBase
{
    void validateEquality(Object expected, Object actual)
    {
        assertThat(actual,is(equalTo(expected)));
    }
    void validateInEquality(Object notExpected, Object actual)
    {
        assertThat(actual,is(not(notExpected)));
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
    void validateSame(Object one, Object two)
    {
        assertThat(one.hashCode(),is(equalTo(two.hashCode())));
    }
    void validateNotSame(Object one, Object two)
    {
        assertThat(one.hashCode(),is( not(equalTo(two.hashCode()))));
    }
    <T> void validateAnyEquals(T[]expecteds, T actual)
    {
        final long count = Arrays.asList(expecteds).stream().filter((expected)->expected.equals(actual)).count();
        assertThat(true,is(count > 0));
    }
    void verifyTestInstance(Object testObject, Object[]args)
    {
        assertNotNull(testObject);
        Arrays.asList(args).forEach((arg)-> validateTrue(valueIsInFields(testObject,arg)));
    }

    private Boolean valueIsInFields(Object instance, Object expectedValue)
    {
        final Long count = Arrays.asList(instance.getClass().getDeclaredFields()).stream().filter((field -> valueIsInField(instance,expectedValue,field))).count();
        return count > 0;
    }
    private Boolean valueIsInField(Object instance, Object expectedValue, Field field)
    {
        final AtomicBoolean found = new AtomicBoolean(false);
        if(null != field && null != instance)
        {
            try
            {
                field.setAccessible(true);
                final Object actual = field.get(instance);
                found.set(expectedValue.equals(actual) || expectedValue == actual);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return found.get();
    }
}
