package companyB.cache.test.memory;

import companyB.cache.test.ExternalCacheTestBase;
import companyB.common.cache.impl.memory.MemoryExternalCache;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@Test(groups = {"unit","http.cache.enabled","external.cache","memory.external.cache","memory.external.cache.no.initial.load"})
public class MemoryExternalCacheNoInitialLoadTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        super.before();
        externalCache = new MemoryExternalCache(name);
        assertNotNull(externalCache);
        assertEquals(name,externalCache.getName());
    }
}
