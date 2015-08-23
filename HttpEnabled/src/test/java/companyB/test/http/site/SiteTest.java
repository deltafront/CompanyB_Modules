package companyB.test.http.site;

import companyB.http.site.IsoLang;
import companyB.http.site.IsoLocale;
import companyB.http.site.Site;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","site","http.site.enabled"})
public class SiteTest
{
    private IsoLang primaryIsoLang;
    private IsoLang[]isoLangs;
    private IsoLocale isoLocale;
    private String name;
    private String id;


    @BeforeMethod
    public void before()
    {
        primaryIsoLang = IsoLang.English;
        isoLangs = IsoLang.values();
        isoLocale = IsoLocale.United_States;
        name = "Foo";
        id = "Bar";
    }


    public void nullLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,null,isoLocale);
        validateSite(site);

    }

    public void zeroLenLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[0],isoLocale);
        validateSite(site);

    }

    public void nonEmptyLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        validateSite(site);
    }

    public void emptyLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[100],isoLocale);
        validateSite(site);
    }


    public void allLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,isoLangs,isoLocale);
        validateSite(site);
    }
    private void validateSite(Site site)
    {
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(), primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());
    }
}
