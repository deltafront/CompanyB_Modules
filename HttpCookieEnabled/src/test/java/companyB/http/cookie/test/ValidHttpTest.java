package companyB.http.cookie.test;

import org.testng.annotations.Test;

@Test(groups = {"unit","cookie.http.reader","http.cookie.enabled"})
public class ValidHttpTest extends CookieReaderTestBase
{
    public void validHttpOnly()
    {
        for (String val : boolVals)
        {
            httpOnly = val;
            doTest(true);
        }
    }
}
