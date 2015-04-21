package companyB.http.cookie.filter.impl;

import companyB.http.cookie.filter.CookieFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of CookieFilter that filters cookies on their name.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class CookieNamesFilter implements CookieFilter
{
    private final String[]validNames;

    /**
     * @param validNames varargs list of valid names. This must not be empty.
     * @since 2.0.0
     */
    public CookieNamesFilter(String...validNames)
    {
        Validate.notEmpty(validNames);
        this.validNames = validNames;
    }
    @Override
    public List<Cookie> filter(Cookie[] cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(Cookie cookie : cookies)
        {
            final boolean contains = ArrayUtils.contains(validNames,cookie.getName());
            LOGGER.trace(String.format("Cookie name '%s' in list of valid names? %b",
                    cookie.getName(),contains));
            if(contains)cookieList.add(cookie);
        }

        LOGGER.trace(String.format("Returning %d cookies.",cookieList.size()));
        return cookieList;
    }
}
