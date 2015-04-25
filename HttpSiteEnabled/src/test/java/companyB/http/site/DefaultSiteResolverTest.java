package companyB.http.site;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test(groups = {"unit","http.site.enabled","default.site.resolver"})
public class DefaultSiteResolverTest
{
    private SiteResolver siteResolver;
    @BeforeMethod
    public void before()
    {
        siteResolver = new DefaultSiteResolver();
    }

    public void testHappyPathPortSpecified()
    {
        String host = "localhost";
        Integer port = 8080;
        Site site = siteResolver.resolveSite(host,port);
        assertNotNull(site);
        assertEquals(String.format("%s%s",host,port),site.getSiteName());
        assertEquals("0",site.getSiteId());
        assertEquals(IsoLanguage.English,site.getPrimaryLanguage());
        assertEquals(1,site.getSupportedLanguages().length);
        assertEquals(IsoLanguage.English,site.getSupportedLanguages()[0]);
        assertEquals(IsoLocale.United_States,site.getLocale());
        assertNotNull(site.toString());
    }
    public void testHappyPathPortNotSpecified()
    {
        String host = "localhost";
        Site site = siteResolver.resolveSite(host,null);
        assertNotNull(site);
        assertEquals(host, site.getSiteName());
        assertEquals("0",site.getSiteId());
        assertEquals(IsoLanguage.English,site.getPrimaryLanguage());
        assertEquals(1,site.getSupportedLanguages().length);
        assertEquals(IsoLanguage.English,site.getSupportedLanguages()[0]);
        assertEquals(IsoLocale.United_States,site.getLocale());
        assertNotNull(site.toString());
    }


}
