package companyB.test.http.cookie;

import companyB.http.cookie.DefaultCookie;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

@Test(groups = {"unit","http.cookie.enabled","default.cookie"})
public class DefaultCookieTest extends TestBase
{
    private Cookie cookie;
    @BeforeMethod
    public void before()
    {
        DefaultCookie defaultCookie = getDefaultCookies().get(0);
        cookie  = defaultCookie.getCookie();
    }
    public void testHappyPathTrue()
    {
        DefaultCookie defaultCookie = new DefaultCookie(cookie,true);
        assertNotNull(defaultCookie);
        assertTrue(defaultCookie.isReplaceIfExists());
    }
    public void testHappyPathFalse()
    {
        DefaultCookie defaultCookie = new DefaultCookie(cookie,false);
        assertNotNull(defaultCookie);
        assertFalse(defaultCookie.isReplaceIfExists());
    }
}
