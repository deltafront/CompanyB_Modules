package companyB.http.cookie.filter.impl;

import companyB.http.cookie.filter.CookieFilter;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class ValidDomainCookieFilter implements CookieFilter
{
    private final String[]validDomains;
    public ValidDomainCookieFilter(String...validDomains)
    {
        this.validDomains = validDomains;
    }
    @Override
    public List<Cookie> filter(Cookie[] cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(final Cookie cookie : cookies)
        {
            for(final String domain : validDomains)
            {
                LOGGER.trace(String.format("Evaluating cookie domain %s against allowed domain %s.",
                        cookie.getDomain(), domain));
                if (cookie.getDomain().equals(domain))
                {
                    cookieList.add(cookie);
                }
            }
        }
        LOGGER.trace(String.format("Returning %d cookies.",cookieList.size()));
        return cookieList;
    }

}
