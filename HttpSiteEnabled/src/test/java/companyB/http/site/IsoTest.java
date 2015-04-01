package companyB.http.site;

import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","iso","http.session.enabled"})
public class IsoTest
{

    public void testIsoLangNonEmpty()
    {
        IsoLang[]isoLangs = IsoLang.values();
        for(IsoLang isoLang : isoLangs)
        {
            assertNotNull(String.format("No abbreviation for IsoLang '%s'.",isoLang),isoLang.abbr);
        }
    }

    public void testIsoLocaleNonEmpty()
    {
        IsoLocale[]isoLocales = IsoLocale.values();
        for(IsoLocale isoLocale : isoLocales)
        {
            assertNotNull(String.format("No abbreviation for IsoLocale '%s'.",isoLocale),isoLocale.abbr);
        }
    }
}
