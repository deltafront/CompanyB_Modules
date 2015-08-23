package companyB.test.http.cookie;

import companyB.http.cookie.CookieWriter;
import org.apache.commons.codec.digest.DigestUtils;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;

import static org.testng.Assert.*;

@Test(groups = {"unit","http.cache.enabled","cookie.writer"})
public class CookieWriterTest extends HttpTestBase
{
    private CookieWriter cookieWriterCache;
    private CookieWriter cookieWriterNoCache;

    @BeforeMethod
    public void before()
    {
        super.before();
        cookieWriterCache = new CookieWriter(externalCache);
        cookieWriterNoCache = new CookieWriter();
        externalCache.insert(cookieCacheValue,valueFromCache);
    }

    public void writeCookieIntoCache()
    {
        Cookie cookie = getCookie(cookieCacheName, cookieCacheValue,cookieCacheDomain);
        response.addCookie(cookie);
        EasyMock.expectLastCall();
        control.replay();
        cookieWriterCache.writeCookie(cookie,response);
        String value = externalCache.retrieve(DigestUtils.md5Hex(String.format("%s%s",cookie.getName(),cookie.getDomain())));
        assertNotNull(value);
        assertEquals(cookieCacheValue,value);
    }
    public void writeCookieNoCache()
    {
        Cookie cookie = getCookie(cookieNoCacheName, cookieNoCacheValue,cookieNoCacheDomain);
        response.addCookie(cookie);
        EasyMock.expectLastCall();
        control.replay();
        cookieWriterNoCache.writeCookie(cookie,response);
        String value = externalCache.retrieve(DigestUtils.md5Hex(String.format("%s%s",cookie.getName(),cookie.getDomain())));
        assertNull(value);
    }
}