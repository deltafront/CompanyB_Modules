package companyB.test.http.cookie;

import companyB.http.cookie.DefaultCookie;
import companyB.http.cookie.DefaultCookieUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Test(groups = {"unit","get.default.cookie","http.cookie.enabled"})
public class GetDefaultCookieTest extends TestBase
{
    private IMocksControl control;
    private HttpServletRequest request;
    private List<DefaultCookie> defaultCookieList;
    private Cookie[]cookies;
    private DefaultCookieUtils cookieUtils;

    @BeforeMethod
    public void before()
    {
        control = EasyMock.createStrictControl();
        request = control.createMock(HttpServletRequest.class);
        defaultCookieList = getDefaultCookies();
        cookies = new Cookie[defaultCookieList.size() + 1];
        defaultCookieList = getDefaultCookies();
        cookieUtils = new DefaultCookieUtils(defaultCookieList);
        for(int i = 0; i < defaultCookieList.size(); i++) cookies[i]= defaultCookieList.get(i).getCookie();
        cookies[defaultCookieList.size()] = new Cookie("nondefault", "nondefault");
    }

    @AfterMethod
    public void after()
    {
        control.verify();
    }


    public void getValidDefaultCookiePresent()
    {
        EasyMock.expect(request.getCookies()).andReturn(cookies).anyTimes();
        control.replay();
        for(DefaultCookie defaultCookie : defaultCookieList)
        {
            Cookie cookie = defaultCookie.getCookie();
            Cookie fromRequest = cookieUtils.getDefaultCookie(cookie.getName(),request);
            assertNotNull(String.format("Failed on cookie name %s.",cookie.getName()),fromRequest);
            assertEquals(cookie.getValue(), fromRequest.getValue());
        }
    }


    public void getValidDefaultCookieNotPresent()
    {
        Cookie lone = defaultCookieList.get(0).getCookie();
        Cookie valid = defaultCookieList.get(1).getCookie();

        EasyMock.expect(request.getCookies()).andReturn(new Cookie[]{lone}).anyTimes();
        control.replay();
        Cookie fromRequest = cookieUtils.getDefaultCookie(valid.getName(),request);
        assertNull(fromRequest);
    }


    public void getAllValidDefaultCookiesWhenPresent()
    {
        EasyMock.expect(request.getCookies()).andReturn(cookies).anyTimes();
        control.replay();
        List<Cookie>cookieList = cookieUtils.getDefaultCookies(request);
        assertEquals(defaultCookieList.size(),cookieList.size());
    }


    public void getAllValidDefaultCookiesWhenNotPresent()
    {
        EasyMock.expect(request.getCookies()).andReturn(null).anyTimes();
        control.replay();
        List<Cookie>cookieList = cookieUtils.getDefaultCookies(request);
        assertEquals(0,cookieList.size());
    }


    public void getInValidDefaultCookie()
    {
        EasyMock.expect(request.getCookies()).andReturn(cookies).anyTimes();
        control.replay();
        assertNull(cookieUtils.getDefaultCookie("invalid",request));
    }
}
