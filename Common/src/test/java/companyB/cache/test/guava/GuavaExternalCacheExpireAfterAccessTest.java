package companyB.cache.test.guava;

import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.test.ExternalCacheTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Test(groups = {"unit","http.cache.enabled","external.cache","guava.external.cache","guava.external.cache.expire.after.access"})
@SuppressWarnings("unchecked")
public class GuavaExternalCacheExpireAfterAccessTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = GuavaExternalCache.class.getCanonicalName();
        externalCache = new GuavaExternalCache(name,1000L, TimeUnit.MILLISECONDS,true);
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }

    public void expireAfterAccess() throws InterruptedException
    {
        final String expected = "value";
        externalCache.insert("key",expected);
        final String actual = (String)externalCache.retrieve("key");
        validateEquality(expected,actual);
        Thread.sleep(1500L);
        validateNull(externalCache.retrieve("key"));
    }
}
