package companyB.cache.test.test.cache.guava;

import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.test.test.cache.ExternalCacheTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertNull;


@SuppressWarnings("unchecked")
@Test(groups = {"unit","http.cache.enabled","external.cache","guava.external.cache","guava.external.cache.expire.after.write"})
public class GuavaExternalCacheExpireAfterWriteTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = GuavaExternalCache.class.getCanonicalName();
        super.before();
        externalCache = new GuavaExternalCache(name,1000L, TimeUnit.MILLISECONDS,false);
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }
    public void expireAfterWrite() throws InterruptedException
    {
        externalCache.insert("key", "value");
        Thread.sleep(1001L);
        assertNull(externalCache.retrieve("key"));
    }
}
