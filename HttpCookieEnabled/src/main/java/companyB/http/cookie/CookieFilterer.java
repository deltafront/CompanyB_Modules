package companyB.http.cookie;

import companyB.http.cookie.filter.CookieFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Filters cookies from HttpServlet requests.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class CookieFilterer
{
    private List<CookieFilter>cookieFilters;
    private final static Logger LOGGER = LoggerFactory.getLogger(CookieFilterer.class);
    public CookieFilterer(List<CookieFilter> cookieFilters)
    {
        this.cookieFilters = cookieFilters;
    }

    /**
     * Filters out all of the cookies that meet specific criteria.
     * @param request HttpServletRequest that contains the cookies.
     * @return List of all cookies that are still valid after being filtered.
     */
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
