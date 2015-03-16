package companyB.http.site;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class IsoTest
{
    @Test
    public void testIsoLangNonEmpty()
    {
        IsoLang[]isoLangs = IsoLang.values();
        for(IsoLang isoLang : isoLangs)
        {
            assertNotNull(String.format("No abbreviation for IsoLang '%s'.",isoLang),isoLang.abbr);
        }
    }
    @Test
    public void testIsoLocaleNonEmpty()
    {
        IsoLocale[]isoLocales = IsoLocale.values();
        for(IsoLocale isoLocale : isoLocales)
        {
            assertNotNull(String.format("No abbreviation for IsoLocale '%s'.",isoLocale),isoLocale.abbr);
        }
    }
}
