package companyB.cache.test.redis;

import companyB.cache.test.ExternalCacheTestBase;
import companyB.cache.impl.redis.RedisExternalCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

@Test(groups = {"unit","http.cache.enabled","external.cache","redis.external.cache"})
public class RedisExternalCacheTest extends ExternalCacheTestBase
{
    private Integer db;
    private String host;
    private Integer port;

    @BeforeMethod
    public void before()
    {
        super.before();
        name = RedisExternalCache.class.getCanonicalName();
        db = 4;
        host = "127.0.0.1";
        port = 6379;
        externalCache = new RedisExternalCache(host,port,db,name);
        assertNotNull(externalCache);
        assertNotNull(((RedisExternalCache)externalCache).ping());
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }

}
