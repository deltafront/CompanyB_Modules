package companyB.http.site;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;


public class SiteTest
{
    private IsoLang primaryIsoLang;
    private IsoLang[]isoLangs;
    private IsoLocale isoLocale;
    private String name;
    private String id;


    @Before
    public void before()
    {
        primaryIsoLang = IsoLang.English;
        isoLangs = IsoLang.values();
        isoLocale = IsoLocale.United_States;
        name = "Foo";
        id = "Bar";
    }

    @Test
    public void nullLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,null,isoLocale);
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(),primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());

    }
    @Test
    public void zeroLenLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[0],isoLocale);
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(),primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());

    }
    @Test
    public void nonEmptyLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[]{IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},isoLocale);
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(),primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());
    }
    @Test
    public void emptyLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,new IsoLang[100],isoLocale);
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(),primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());
    }

    @Test
    public void allLangs()
    {
        Site site = new Site(name,id,primaryIsoLang,isoLangs,isoLocale);
        assertNotNull(site);
        assertEquals(name,site.getSiteName());
        assertEquals(id,site.getSiteId());
        assertEquals(primaryIsoLang,site.getPrimaryLang());
        assertTrue(ArrayUtils.contains(site.getSupportedLangs(),primaryIsoLang));
        assertEquals(isoLocale,site.getLocale());
        assertNotNull(site.getHostIpAddress());
        assertNotNull(site.getHostName());
    }

}
