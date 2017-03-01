package companyB.cache.test;

import companyB.cache.ExternalCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class ExternalCacheTestBase
{
    protected ExternalCache externalCache;
    protected String name;
    protected String key;
    protected Integer max_num;

    @BeforeMethod
    public void before()
    {
        max_num = 1000;
        key = "foo";
    }
    @AfterMethod
    public void after()
    {
        if(null != externalCache)
            externalCache.clear();
    }
}
