package companyB.cache.test.test.cache.memory;

import companyB.cache.impl.memory.MemoryExternalCache;
import companyB.cache.test.test.cache.ExternalCacheTestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","http.cache.enabled","external.cache","memory.external.cache","memory.external.cache.initial.load"})
public class MemoryExternalCacheInitialLoadTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        super.before();
        externalCache = new MemoryExternalCache(name,max_num);
        assertNotNull(externalCache);
        assertEquals(name,externalCache.getName());
    }

}
