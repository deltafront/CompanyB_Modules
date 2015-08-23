package companyB.http.cookie.test;

import companyB.cache.ExternalCache;
import companyB.cache.impl.memory.MemoryExternalCache;
import companyB.http.cookie.CookieReader;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpTestBase
{
    protected ExternalCache<String,String> externalCache;
    protected  IMocksControl control;
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected CookieReader cookieReaderCache;
    protected CookieReader cookieReaderNoCache;
    protected String cookieCacheName = "TestCookieCache";
    protected String cookieCacheDomain = "TestCookieDomainCache";
    protected String cookieNoCacheName = "TestCookieNoCache";
    protected String cookieNoCacheDomain = "TestCookieDomainNoCache";
    protected String cookieCacheValue = "TestCookieValueCache";
    protected String cookieNoCacheValue = "TestCookieValueNoCache";
    protected String valueFromCache = "CookieValueFromCache";
    @BeforeMethod
    public void before()
    {
        externalCache = new MemoryExternalCache("Test",100);
        control = EasyMock.createNiceControl();
        request = control.createMock(HttpServletRequest.class);
        response = control.createMock(HttpServletResponse.class);
    }
    protected Cookie getCookie(String name, String value,String domain)
    {
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        return cookie;
    }
}
