package companyB.http.site;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.testng.Assert.assertNull;

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
    public void nonEmptyLangsIncludesPrimary()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{primaryIsoLang,IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
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

    public void getResourceFileHappyPathIncludesTrailingSlash()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{primaryIsoLang,IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        String dir = "/foo/";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename,primaryIsoLang,isoLocale);
        assertNotNull(fromSite);
        assertEquals(String.format("%s%s_%s_%s.properties", dir, filename, primaryIsoLang.name(), isoLocale.name()), fromSite);
    }

    public void getResourceFileHappyPathDoesNotIncludeTrailingSlash()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{primaryIsoLang,IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename,primaryIsoLang,isoLocale);
        assertNotNull(fromSite);
        assertEquals(String.format("%s/%s_%s_%s.properties", dir, filename, primaryIsoLang.name(), isoLocale.name()), fromSite);
    }
    public void getResourceFileInvalidLocale()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{primaryIsoLang,IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename,primaryIsoLang,IsoLocale.Afghanistan);
        assertNull(fromSite);
    }
    public void getResourceFileInvalidLanguage()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{primaryIsoLang,IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename,IsoLang.Arabic,isoLocale);
        assertNull(fromSite);
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
