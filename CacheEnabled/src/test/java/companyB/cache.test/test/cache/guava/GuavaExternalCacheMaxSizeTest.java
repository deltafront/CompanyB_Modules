package companyB.cache.test.test.cache.guava;

import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.test.test.cache.ExternalCacheTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
        externalCache.clear();
    }

    public void testMaxSize()
    {
        externalCache.insert("foo","43");
        String value = "43";
        assertEquals(value,externalCache.retrieve("foo"));
        externalCache.insert("one","two");
        assertNull(externalCache.retrieve("foo"));
    }
}
