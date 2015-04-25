package companyB.http.site;

import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","iso","http.site.enabled"})
public class IsoTest
{

    public void testIsoLangNonEmpty()
    {
        IsoLanguage[] isoLanguages = IsoLanguage.values();
        for(IsoLanguage isoLanguage : isoLanguages)
            assertNotNull(String.format("No abbreviation for IsoLanguage '%s'.", isoLanguage), isoLanguage.abbr);
    }

    public void testIsoLocaleNonEmpty()
    {
        IsoLocale[]isoLocales = IsoLocale.values();
        for(IsoLocale isoLocale : isoLocales)
            assertNotNull(String.format("No abbreviation for IsoLocale '%s'.",isoLocale),isoLocale.abbr);
    }
}
