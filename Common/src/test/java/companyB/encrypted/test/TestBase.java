package companyB.encrypted.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TestBase
{
    void validateEquality(Object expected, Object actual)
    {
        assertThat(expected,is(equalTo(actual)));
    }
    void validateNonEquality(Object notExpected, Object actual)
    {
        assertThat(notExpected,is(not(equalTo(actual))));
    }
    void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
}
