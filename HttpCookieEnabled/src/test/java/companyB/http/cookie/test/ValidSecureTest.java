package companyB.http.cookie.test;

import org.testng.annotations.Test;

@Test(groups = {"unit","cookie.secure.reader","http.cookie.enabled"})
public class ValidSecureTest extends CookieReaderTestBase
{
    public void validSecure()
    {
        for (String val : boolVals)
        {
            secure = val;
            doTest(true);
        }
    }
}
