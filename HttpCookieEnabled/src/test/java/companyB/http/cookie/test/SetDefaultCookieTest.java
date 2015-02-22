package companyB.http.cookie.test;

import companyB.http.cookie.DefaultCookie;
import companyB.http.cookie.DefaultCookieUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SetDefaultCookieTest extends TestBase
{


    private IMocksControl control;
    private HttpServletResponse response;
    private List<DefaultCookie> defaultCookieList;
    private DefaultCookieUtils cookieUtils;

    @Before
    public void before()
    {
        control = EasyMock.createNiceControl();
        response = control.createMock(HttpServletResponse.class);
        control.replay();
        defaultCookieList = getDefaultCookies();
        cookieUtils = new DefaultCookieUtils(defaultCookieList);
    }

    @After
    public void after()
    {
        control.verify();
    }

    @Test
    public void setDefaultCookieCookiePresent()
    {

        for(DefaultCookie defaultCookie : defaultCookieList)
        {
            Cookie cookie = defaultCookie.getCookie();
            assertTrue(cookieUtils.setDefaultCookieValue(cookie.getName(),cookie.getValue(),response));
        }
    }
    @Test
    public void setDefaultCookieCookieNotPresent()
    {
        assertFalse(cookieUtils.setDefaultCookieValue("fake","value",response));
    }

    @Test
    public void setDefaultCookiesToResponse()
    {
        assertEquals(defaultCookieList.size(),cookieUtils.setDefaultCookies(response));
    }
}
