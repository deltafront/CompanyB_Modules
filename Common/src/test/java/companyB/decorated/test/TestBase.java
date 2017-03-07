package companyB.decorated.test;

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
    void validateNull(Object instance)
    {
        assertThat(instance,is(nullValue()));
    }
    void validateNotNull(Object instance)
    {
        assertThat(instance,is(not(nullValue())));
    }
}
