package companyB.cache.test.test.cache.guava;

import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.test.test.cache.ExternalCacheTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNull;

@Test(groups = {"unit","http.cache.enabled","external.cache","guava.external.cache","guava.external.cache.expire.after.access"})
@SuppressWarnings("unchecked")
public class GuavaExternalCacheExpireAfterAccessTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = GuavaExternalCache.class.getCanonicalName();
        super.before();
        externalCache = new GuavaExternalCache(name,1000L, TimeUnit.MILLISECONDS,true);
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }
    public void expireAfterAccess() throws InterruptedException
    {
        externalCache.insert("key","value");
        assertEquals("value", externalCache.retrieve("key"));
        Thread.sleep(1500L);
        assertNull(externalCache.retrieve("key"));
    }
}
