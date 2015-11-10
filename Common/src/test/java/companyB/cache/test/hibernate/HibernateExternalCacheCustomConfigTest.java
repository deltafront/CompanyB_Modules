package companyB.cache.test.hibernate;

import companyB.cache.test.ExternalCacheTestBase;
import companyB.cache.impl.db.CacheEntry;
import companyB.cache.impl.db.HibernateExternalCache;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

@Test(groups = {"unit","http.cache.enabled","external.cache","hibernate.external.cache","hibernate.external.cache.default.config"})
public class HibernateExternalCacheCustomConfigTest extends ExternalCacheTestBase
{
    @BeforeMethod
    public void before()
    {
        name = HibernateExternalCache.class.getCanonicalName();
        super.before();
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.connection.driver_class","org.h2.Driver");
        properties.put("hibernate.connection.url","jdbc:h2:mem:db");
        properties.put("hibernate.connection.username","sa");
        properties.put("hibernate.connection.password","");
        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("show_sql","true");
        configuration.addAnnotatedClass(CacheEntry.class);
        configuration.setProperties(properties);
        externalCache = new HibernateExternalCache(name,configuration);
    }
    @AfterMethod
    public void after()
    {
        externalCache.clear();
    }
}
