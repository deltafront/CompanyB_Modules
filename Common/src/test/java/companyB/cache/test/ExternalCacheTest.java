package companyB.cache.test;

import companyB.cache.ExternalCache;
import companyB.cache.impl.guava.GuavaExternalCache;
import companyB.cache.impl.memory.MemoryExternalCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unchecked", "BoxingBoxedValue"})
@Test(groups = {"unit","external.cache"}, dataProvider = "default")
public class ExternalCacheTest extends ExternalCacheTestBase
{
    private ExternalCache<String,String> externalCache;
    protected String key = "foo";

    @DataProvider(name = "default")
    public static Object[][] parameters()
    {
        return new Object[][]{
                {"GuavaExpireAfterAccess", new GuavaExternalCache("GuavaExpireAfterAccess", 1000L, TimeUnit.MILLISECONDS, true)},
                {"GuavaExpireAfterWrite", new GuavaExternalCache("GuavaExpireAfterWrite", 1000L, TimeUnit.MILLISECONDS, false)},
                {"GuavaMaxSize", new GuavaExternalCache("GuavaMaxSize", 1)},
                {"MemoryInitialLoad", new MemoryExternalCache("MemoryInitialLoad", 1000)},
                {"MemoryNoInitialLoad", new MemoryExternalCache("MemoryNoInitialLoad")}
        };
    }
    @AfterMethod
    public void after()
    {
        if(null != this.externalCache)
            externalCache.clear();
    }
    public void verifyName(String name, ExternalCache externalCache)
    {
        validateCache(externalCache);
        validateEquality(name,externalCache.getName());
    }
    public void testRemoveValue(String name, ExternalCache<String,String>externalCache)
    {
        insertValue(externalCache);
        String value = "42";
        String fromCache = this.externalCache.remove("foo");
        validateNotNull(fromCache);
        validateEquality(value,fromCache);
        validateNull(externalCache.remove("foo"));
    }
    public void insertValue(String name, ExternalCache<String,String>externalCache)
    {
        insertValue(externalCache);
    }
    public void updateValue(String name, ExternalCache<String,String>externalCache)
    {
        insertValue(externalCache);
        String value = "43";
        externalCache.insert(key, value);
        validateEquality(value,externalCache.retrieve(key));
    }

    public void nullInsert(String name, ExternalCache<String,String>externalCache)
    {
        validateCache(externalCache);
        this.externalCache.insert(key, null);
        validateNull(this.externalCache.retrieve(key));
    }
    public void testRemoveInvalidValue(String name, ExternalCache<String,String>externalCache)
    {
        validateCache(externalCache);
        validateNull(this.externalCache.retrieve("FOO"));
    }
    private void insertValue(ExternalCache<String,String>externalCache)
    {
        validateCache(externalCache);
        String value = "42";
        this.externalCache.insert(key, value);
        validateEquality(value,externalCache.retrieve(key));
    }
    private void validateCache(ExternalCache externalCache)
    {
        validateNotNull(externalCache);
        this.externalCache = externalCache;
    }
}
