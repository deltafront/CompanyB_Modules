package companyB.http.cookie.test;

import companyB.http.cookie.DefaultCookie;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

public class TestBase
{


    protected enum DefaultCookieNames
    {
        foo_1,foo_2;
        private DefaultCookieNames()
        {
            this.value = String.format("%s_value",name());
        }
        public String value;
    }
    protected List<DefaultCookie>getDefaultCookies()
    {
        List<DefaultCookie>defaultCookies = new LinkedList<>();
        for(DefaultCookieNames defaultCookieName : DefaultCookieNames.values())
        {
            Cookie cookie = new Cookie(defaultCookieName.name(),defaultCookieName.value);
            defaultCookies.add(new DefaultCookie(cookie,true));
        }
        return defaultCookies;
    }
}
