package companyB.http.cookie.test;

import companyB.http.cookie.filter.CookieFilter;
import companyB.http.cookie.filter.impl.*;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class CookieFilterTest extends TestBase
{
    @Test
    public void testDomainPatternFilter()
    {
        Cookie[]cookies = new Cookie[100];
        for(int i = 0; i < 100; i++)
        {
            String name = (i%10 ==0) ? String.format("foo_%s",i%10) : String.format("bar_%s",i);
            Cookie cookie = new Cookie(name, String.format("Cookie value %s",i));
            cookie.setDomain(String.format("%s.com",name));
            cookies[i] = cookie;
        }
        CookieFilter filter = new CookieDomainPatternFilter("foo_[0-9].com");
        List<Cookie>fromFilter = filter.filter(cookies);
        assertNotNull(fromFilter);
        assertEquals(10,fromFilter.size());
    }

    @Test
    public void testCookieValueSizeFilter()
    {
        Cookie[]cookies = new Cookie[100];
        for(int i = 0; i < 100; i++)
        {
            String name = (i%10 ==0) ? String.format("foo_%s",i%10) : String.format("bar_%s",i);
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies[i] = cookie;
        }
        CookieFilter filter = new CookieValueSizeFilter(900);
        List<Cookie>fromFilter = filter.filter(cookies);
        assertNotNull(fromFilter);
        assertEquals(90,fromFilter.size());
    }

    @Test
    public void testInvalidDomainFilter()
    {
        Cookie[]cookies = new Cookie[100];
        for(int i = 0; i < 100; i++)
        {
            String name = (i%10 ==0) ? "foo" : "bar";
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies[i] = cookie;
        }
        CookieFilter filter = new InvalidDomainCookieFilter("foo.com");
        List<Cookie>fromFilter = filter.filter(cookies);
        assertNotNull(fromFilter);
        assertEquals(90,fromFilter.size());
    }
    @Test
    public void testValidDomainFilter()
    {
        Cookie[]cookies = new Cookie[100];
        for(int i = 0; i < 100; i++)
        {
            String name = (i%10 ==0) ? "foo" : "bar";
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies[i] = cookie;
        }
        CookieFilter filter = new ValidDomainCookieFilter("foo.com");
        List<Cookie>fromFilter = filter.filter(cookies);
        assertNotNull(fromFilter);
        assertEquals(10,fromFilter.size());
    }
    @Test
    public void testCookieNamesFilter()
    {

        Cookie[]cookies = new Cookie[100];
        for(int i = 0; i < 100; i++)
        {
            String name =   (i%10 ==0) ?    "ten"   :
                            (i%5==0) ?      "five"  :
                            (i%3==0) ?      "three" :
                            (i%2==0) ?      "two"   :
                             "none";
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies[i] = cookie;
        }
        CookieFilter filter = new CookieNamesFilter("ten","five","three","two");
        List<Cookie>fromFilter = filter.filter(cookies);
        assertNotNull(fromFilter);
        assertEquals(74,fromFilter.size());
    }
}
