package companyB.http.cookie;

import companyB.http.cookie.filter.CookieFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class CookieFilterer
{
    private List<CookieFilter>cookieFilters;
    private final static Logger LOGGER = LoggerFactory.getLogger(CookieFilterer.class);
    public CookieFilterer(List<CookieFilter> cookieFilters)
    {
        this.cookieFilters = cookieFilters;
    }

    public List<Cookie>processCookies(HttpServletRequest request)
    {
        List<Cookie>cookies = new LinkedList<>();
        for(CookieFilter cookieFilter : cookieFilters)
        {
            cookies = cookieFilter.filter(request.getCookies());
            LOGGER.trace(String.format("Returning %d cookies filtered by %s.",
                    cookies.size(),cookieFilter.getClass().getCanonicalName()));
        }
        LOGGER.trace(String.format("Returning %d cookies.",cookies.size()));
        return cookies;
    }
}
