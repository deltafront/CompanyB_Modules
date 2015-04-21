package companyB.http.cookie.test;

import companyB.http.cookie.CookieFilterer;
import companyB.http.cookie.filter.CookieFilter;
import companyB.http.cookie.filter.impl.CookieValueSizeFilter;
import companyB.http.cookie.filter.impl.InvalidDomainCookieFilter;
import companyB.http.cookie.filter.impl.ValidDomainCookieFilter;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test(groups = {"unit","http.cookie.enabled","cookie.filterer"})
public class CookieFiltererTest extends TestBase
{
    private CookieFilterer cookieFilterer;
    private HttpServletRequest request;
    private Cookie[]cookieArray;
    private IMocksControl control;
    @BeforeMethod
    public void before()
    {
        List<Cookie>cookies = new LinkedList<>();
        for(int i = 0; i < 100; i++)
        {
            String name = "baz";
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies.add(cookie);
        }
        for(int i = 0; i < 100; i++)
        {
            String name = (i%10 ==0) ? "foo" : "bar";
            String value = (i%10==0) ? writeBytes(1000) : writeBytes(500);
            Cookie cookie = new Cookie(name,value);
            cookie.setDomain(String.format("%s.com",name));
            cookies.add(cookie);
        }
        List<CookieFilter>cookieFilters = new LinkedList<>();
        cookieFilters.add(new InvalidDomainCookieFilter("foo.com"));
        cookieFilters.add(new CookieValueSizeFilter(900));
        cookieFilters.add(new ValidDomainCookieFilter("bar.com"));
        cookieArray = cookies.toArray(new Cookie[cookies.size()]);
        cookieFilterer = new CookieFilterer(cookieFilters);
        control = EasyMock.createControl();
    }

    public void doChain()
    {
        try
        {
            request = control.createMock(HttpServletRequest.class);
            EasyMock.expect(request.getCookies()).andReturn(cookieArray).anyTimes();
            control.replay();
            List<Cookie>fromFilter = cookieFilterer.processCookies(request);
            assertNotNull(fromFilter);
            assertEquals(90,fromFilter.size());
        }
        finally
        {
            control.verify();
        }

    }
}
