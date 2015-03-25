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
    public enum Mode
    {
        WHITELIST,
        BLACKLIST
    }
    private final String[]domains;
    private final Mode mode;

    public InvalidDomainCookieFilter(Mode mode, String...domains)
    {
        this.mode = mode;
        this.domains = domains;
    }
    @Override
    public List<Cookie> filter(List<Cookie> cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(final Cookie cookie : cookies)
        {
            for(final String domain : domains)
            {
                if (validDomain(cookie.getDomain(),domain))
                {
                   cookieList.add(cookie);
                }
            }
        }
        LOGGER.trace(String.format("Returning %d cookies.",cookieList.size()));
        return cookieList;
    }
    private boolean validDomain(String fromCookie, String domain)
    {
        final boolean valid = (fromCookie.equals(domain) && Mode.WHITELIST.equals(mode));
        LOGGER.trace(String.format("Cookie value %s valid against domain %s (mode %s) ? %b"
                ,fromCookie,domain,mode,valid));
        return valid;
    }

}
