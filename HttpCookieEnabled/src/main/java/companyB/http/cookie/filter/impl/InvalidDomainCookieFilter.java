package companyB.http.cookie.filter.impl;

import companyB.http.cookie.filter.CookieFilter;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class InvalidDomainCookieFilter implements CookieFilter
{
    private final String[] invalidDomains;

    public InvalidDomainCookieFilter(String... invalidDomains)
    {
        this.invalidDomains = invalidDomains;
    }
    @Override
    public List<Cookie> filter(Cookie[] cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(final Cookie cookie : cookies)
        {
            boolean isValid = true;
            for(final String domain : invalidDomains)
            {
                LOGGER.trace(String.format("Evaluating cookie domain %s against disallowed domain %s.",
                        cookie.getDomain(), domain));
                if (cookie.getDomain().equals(domain))
                {
                   isValid = false;
                    break;
                }
            }
            if(isValid)
            {
                cookieList.add(cookie);
            }
        }
        LOGGER.trace(String.format("Returning %d cookies.",cookieList.size()));
        return cookieList;
    }

}
