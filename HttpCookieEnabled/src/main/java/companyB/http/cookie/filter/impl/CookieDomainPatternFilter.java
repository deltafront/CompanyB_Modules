package companyB.http.cookie.filter.impl;

import companyB.http.cookie.filter.CookieFilter;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class CookieDomainPatternFilter implements CookieFilter
{
    private final Pattern pattern;

    public CookieDomainPatternFilter(String regex)
    {
        this.pattern = Pattern.compile(regex);
    }
    @Override
    public List<Cookie> filter(List<Cookie> cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(final Cookie cookie : cookies)
        {
            final String domain = cookie.getDomain();
            final boolean matches = pattern.matcher(domain).matches();
            if(matches)
            {
                cookieList.add(cookie);
            }
            LOGGER.trace(String.format("Cookie domain '%s' matches regex '%s'? %b",
                    domain,pattern.pattern(),matches));
        }
        LOGGER.trace(String.format("Returning %d cookies.", cookieList.size()));
        return cookieList;
    }
}
