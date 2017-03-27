package companyB.cache.test;

import companyB.cache.ExternalCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;


public class ExternalCacheTestBase
{
    protected ExternalCache externalCache;
    protected String name;
    protected String key;
    protected Integer max_num;

    @BeforeMethod
    public void before()
    {
        max_num = 1000;
        key = "foo";
    }
    @AfterMethod
    public void after()
    {
        if(null != externalCache)
            externalCache.clear();
    }
    protected void validateEquality(Object expected, Object actual)
    {
        assertThat(expected,is(equalTo(actual)));
    }
    protected void validateNull(Object instance)
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
