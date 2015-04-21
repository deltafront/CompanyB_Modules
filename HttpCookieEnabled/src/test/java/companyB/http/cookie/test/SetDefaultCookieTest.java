package companyB.http.cookie.test;

import companyB.http.cookie.DefaultCookie;
import companyB.http.cookie.DefaultCookieUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.*;

@Test(groups = {"unit","set.default.cookie","http.cookie.enabled"})
public class SetDefaultCookieTest extends TestBase
{
    private IMocksControl control;
    private HttpServletResponse response;
    private List<DefaultCookie> defaultCookieList;
    private DefaultCookieUtils cookieUtils;

    @BeforeMethod
    public void before()
    {
        control = EasyMock.createNiceControl();
        response = control.createMock(HttpServletResponse.class);
        control.replay();
        defaultCookieList = getDefaultCookies();
        cookieUtils = new DefaultCookieUtils(defaultCookieList);
    }

    @AfterMethod
    public void after()
    {
        control.verify();
    }


    public void setDefaultCookieCookiePresent()
    {

        for(DefaultCookie defaultCookie : defaultCookieList)
        {
            Cookie cookie = defaultCookie.getCookie();
            assertTrue(cookieUtils.setDefaultCookieValue(cookie.getName(),cookie.getValue(),response));
        }
    }

    public void setDefaultCookieCookieNotPresent()
    {
        assertFalse(cookieUtils.setDefaultCookieValue("fake","value",response));
    }


    public void setDefaultCookiesToResponse()
    {
        assertEquals(defaultCookieList.size(),cookieUtils.setDefaultCookies(response));
    }
}
