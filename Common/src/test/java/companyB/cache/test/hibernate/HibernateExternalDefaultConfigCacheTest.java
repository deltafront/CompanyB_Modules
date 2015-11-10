package companyB.cache.test.hibernate;

import companyB.cache.test.ExternalCacheTestBase;
import companyB.common.cache.impl.db.HibernateExternalCache;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {"unit","http.cache.enabled","external.cache","hibernate.external.cache","hibernate.external.cache.default.config"})
public class HibernateExternalDefaultConfigCacheTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = HibernateExternalCache.class.getCanonicalName();
        super.before();
        externalCache = new HibernateExternalCache(name);
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }
}
