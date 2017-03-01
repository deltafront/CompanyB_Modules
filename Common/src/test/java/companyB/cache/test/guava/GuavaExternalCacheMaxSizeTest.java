package companyB.cache.test.guava;

import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.test.ExternalCacheTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Test(groups = {"unit","http.cache.enabled","external.cache","guava.external.cache","guava.external.cache.expire.max.size"})
@SuppressWarnings("unchecked")
public class GuavaExternalCacheMaxSizeTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = GuavaExternalCache.class.getCanonicalName();
        super.before();
        externalCache = new GuavaExternalCache(name,1);
    }
    @AfterMethod
    public void after()
    {
        super.after();
    }

    public void testMaxSize()
    {
        externalCache.insert("foo","43");
        final String value = "43";
        assertThat(value,externalCache.retrieve("foo"),is(equalTo(value)));
        externalCache.insert("one","two");
        assertThat(externalCache.retrieve("foo"),is(nullValue()));
    }
}
