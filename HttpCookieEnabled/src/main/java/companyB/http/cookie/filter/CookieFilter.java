package companyB.http.cookie.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Defines methods for filtering out cookies.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
@SuppressWarnings("PMD.UnusedModifier")
public interface CookieFilter
{
    final static Logger LOGGER = LoggerFactory.getLogger(CookieFilter.class);

    /**
     * Filters out cookies that meet criteria into a list.
     * @param cookies Array of cookies to be filtered.
     * @return All cookies that meet criteria.
     * @since 2.0.0
     */
    public List<Cookie>filter(Cookie[]cookies);
}
