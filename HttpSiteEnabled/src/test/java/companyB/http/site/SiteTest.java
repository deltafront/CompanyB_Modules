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
    private IsoLanguage primaryIsoLanguage;
    private IsoLanguage[] isoLanguages;
    private IsoLocale isoLocale;
    private String name;
    private String id;


    @BeforeMethod
    public void before()
    {
        primaryIsoLanguage = IsoLanguage.English;
        isoLanguages = IsoLanguage.values();
        isoLocale = IsoLocale.United_States;
        name = "Foo";
        id = "Bar";
    }


    public void nullLangs()
    {
        Site site = new Site(name,id, primaryIsoLanguage,null,isoLocale);
        validateSite(site);

    }

    public void zeroLenLangs()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[0],isoLocale);
        validateSite(site);
    }

    public void nonEmptyLangs()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        validateSite(site);
    }
    public void nonEmptyLangsIncludesPrimary()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{primaryIsoLanguage, IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        validateSite(site);
    }

    public void emptyLangs()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[100],isoLocale);
        validateSite(site);
    }

    public void allLangs()
    {
        Site site = new Site(name,id, primaryIsoLanguage, isoLanguages,isoLocale);
        validateSite(site);
    }

    public void getResourceFileHappyPathIncludesTrailingSlash()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{primaryIsoLanguage, IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        String dir = "/foo/";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename, primaryIsoLanguage,isoLocale);
        assertNotNull(fromSite);
        assertEquals(String.format("%s%s_%s_%s.properties", dir, filename, primaryIsoLanguage.name(), isoLocale.name()), fromSite);
    }

    public void getResourceFileHappyPathDoesNotIncludeTrailingSlash()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{primaryIsoLanguage, IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename, primaryIsoLanguage,isoLocale);
        assertNotNull(fromSite);
        assertEquals(String.format("%s/%s_%s_%s.properties", dir, filename, primaryIsoLanguage.name(), isoLocale.name()), fromSite);
    }
    public void getResourceFileInvalidLocale()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{primaryIsoLanguage, IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename, primaryIsoLanguage,IsoLocale.Afghanistan);
        assertNull(fromSite);
    }
    public void getResourceFileInvalidLanguage()
    {
        Site site = new Site(name,id, primaryIsoLanguage,new IsoLanguage[]{primaryIsoLanguage, IsoLanguage.Abkhazian, IsoLanguage.Afan_Oromo, IsoLanguage.Afrikaans},isoLocale);
        String dir = "/foo";
        String filename = "test";
        String fromSite = site.getResourcePropertiesFileName(dir,filename, IsoLanguage.Arabic,isoLocale);
        assertNull(fromSite);
    }
    private void validateSite(Site site)
    {
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLanguage,site.getPrimaryLanguage());
        assertTrue(ArrayUtils.contains(site.getSupportedLanguages(), primaryIsoLanguage));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());
    }
}
