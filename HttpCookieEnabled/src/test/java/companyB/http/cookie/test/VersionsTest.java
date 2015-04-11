package companyB.http.cookie.test;

import org.testng.annotations.Test;

@Test(groups = {"unit","cookie.versions.reader","http.cookie.enabled"})
public class VersionsTest extends CookieReaderTestBase
{
    public void validVersions()
    {
        version = "1";
        doTest(true);
        version = "0";
        doTest(true);
    }

}

