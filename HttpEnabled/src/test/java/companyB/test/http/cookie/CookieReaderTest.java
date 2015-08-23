package companyB.test.http.cookie;

import companyB.http.cookie.CookieReader;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.testng.Assert.*;

@Test(groups = {"unit","http.cache.enabled","cookie.reader"})
public class CookieReaderTest extends HttpTestBase
{
    @BeforeMethod
    public void before()
    {
        super.before();
        control = EasyMock.createNiceControl();
        cookieReaderCache = new CookieReader(externalCache);
        cookieReaderNoCache = new CookieReader();
        externalCache.insert(cookieCacheValue,valueFromCache);
        Cookie cachedCookie = getCookie(cookieCacheName, cookieCacheValue,cookieCacheDomain);
        Cookie noCachedCookie = getCookie(cookieNoCacheName, cookieNoCacheValue, cookieNoCacheDomain);
        Cookie[] cookies = new Cookie[]{cachedCookie,noCachedCookie};
        request = control.createMock(HttpServletRequest.class);
        EasyMock.expect(request.getCookies()).andReturn(cookies);
        EasyMock.expect(request.getCookies()).andReturn(null);
        control.replay();
    }

    public void cookiesNoCache()
    {
        String value = cookieReaderNoCache.readCookie(request,cookieNoCacheName);
        assertNotNull(value);
        assertEquals(cookieNoCacheValue,value);
        verify();
    }

    public void cookiesWithCache()
    {
        String value = cookieReaderCache.readCookie(request,cookieCacheName);
        assertNotNull(value);
        assertEquals(valueFromCache,value);
        verify();
    }
    public void valueNotInCache()
    {
        String value = cookieReaderCache.readCookie(request,cookieNoCacheName);
        assertNull(value);
        verify();
    }
    public void noCookiesInCache()
    {
        externalCache.clear();
        String value = cookieReaderCache.readCookie(request,cookieCacheName);
        assertNull(value);
        verify();
    }
    public void noCookies()
    {
        cookieReaderCache.readCookie(request,cookieCacheName);
        String value = cookieReaderCache.readCookie(request,cookieCacheName);
        assertNull(value);
        cookieReaderNoCache.readCookie(request,cookieNoCacheName);
        control.verify();
    }
    private void verify()
    {
        cookieReaderNoCache.readCookie(request,cookieNoCacheName);
        control.verify();
    }
}
