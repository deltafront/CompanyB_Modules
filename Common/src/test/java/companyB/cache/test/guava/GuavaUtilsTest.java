package companyB.cache.test.guava;

import com.google.common.cache.Cache;
import companyB.cache.utils.GuavaUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertNull;

@Test(groups =  {"unit","http.cache.enabled","external.cache","guava.utils"})
public class GuavaUtilsTest
{
    private GuavaUtils guavaUtils;

    @BeforeMethod
    public void before()
    {
        guavaUtils = new GuavaUtils();
    }
    public void happyPathMaxSize()
    {
        final Cache<String,String> cache = guavaUtils.getMaxSizeCache(10);
        assertThat(cache,is(not(nullValue())));
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void maxSizeFail()
    {
        final Cache<String,String> cache = guavaUtils.getMaxSizeCache(-1);
        assertNull(cache);
    }
    @Test(expectedExceptions = NullPointerException.class)
    public void maxSizeNullFail()
    {
        final Cache<String,String> cache = guavaUtils.getMaxSizeCache(null);
        assertThat(cache,is(nullValue()));
    }
    public void happyPathExpireAfterAccess()
    {
        final Cache<String,String> cache = guavaUtils.getExpireAfterAccessCache(10L, TimeUnit.MINUTES);
        assertThat(cache,is(not(nullValue())));
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void expireAfterAccessFail()
    {
        final Cache<String,String> cache = guavaUtils.getExpireAfterAccessCache(-1L, TimeUnit.MINUTES);
        assertThat(cache,is(nullValue()));
    }
    public void happyPathExpireAfterWrite()
    {
        final Cache<String,String> cache = guavaUtils.getExpireAfterWriteCache(10L, TimeUnit.MINUTES);
        assertThat(cache,is(not(nullValue())));
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void expireAfterWriteFail()
    {
        final Cache<String,String> cache = guavaUtils.getExpireAfterWriteCache(10L, TimeUnit.valueOf("FOO"));
        assertThat(cache,is(nullValue()));
    }
}
