package companyB.http.cookie;

import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Contains methods for (1) setting default cookies within an HttpServletResponse, and (2) getting any default cookie values from
 * an HttpServletRequest.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class DefaultCookieUtils extends UtilityBase
{
    private final List<DefaultCookie>defaultCookies;
    private final Set<String>defaultCookieNames;
    private final CookieUtils cookieUtils;

    /**
     * Default constructor.
     * @param defaultCookies - List of default cookies that need to be either set in a response or fetched from a request.
     * @since 1.0
     */
    public DefaultCookieUtils(List<DefaultCookie>defaultCookies)
    {
        Validate.notNull(defaultCookies,"List of default cookies must be supplied.");
        this.defaultCookies = defaultCookies;
        defaultCookieNames = new HashSet<>();
        for(final DefaultCookie defaultCookie : this.defaultCookies)
            defaultCookieNames.add(defaultCookie.getCookie().getName());
        this.cookieUtils = new CookieUtils();
    }

    /**
     * Sets all of the default cookies with their values in the response.
     * @param response HttpServletResponse in which all of the default cookies are to be set.
     * @return Number of cookies that have been set.
     * @since 1.0
     */
    public int setDefaultCookies(HttpServletResponse response)
    {
        Validate.notNull(response,"Response is required.");
        int counter = 0;
        for(final DefaultCookie defaultCookie : defaultCookies)
        {
            final Cookie cookie = defaultCookie.getCookie();
            response.addCookie(cookie);
            counter++;
            LOGGER.trace(String.format("Added cookie %d of %d to request:\n%s",
                    counter, defaultCookies.size(), cookieUtils.cookieToString(cookie)));
        }
        LOGGER.debug(String.format("Number of cookies set: %d",counter));
        return counter;
    }

    /**
     * Sets the default cookie's value.
     * @param name Name of the default cookie to set.
     * @param value Value to set the Default cookie to.
     * @param response HttpServletResponse in which the default cookie value is to be set.
     * @return true if the cookie has been set.
     * @since 1.0
     */
    public boolean setDefaultCookieValue(String name, String value, HttpServletResponse response)
    {
        Validate.notNull(response,"Response is required.");
        boolean isSet = false;
        if(validateDefaultCookieName(name))
        {
            for(DefaultCookie defaultCookie : defaultCookies)
            {
                final Cookie cookie = defaultCookie.getCookie();
                if(name.equals(cookie.getName()))
                {
                    cookie.setValue(value);
                    response.addCookie(cookie);
                    LOGGER.trace(String.format("Adding Default cookie to response:\n%s", cookieUtils.cookieToString(cookie)));
                    isSet = true;
                }
            }
        }
        LOGGER.debug(String.format("Cookie '%s' set to '%s'? %b",name,value,isSet));
        return isSet;
    }


    /**
     * Gets all of the presently set default cookies from the request.
     * @param request HttpServletRequest to get cookies from.
     * @return List of all of the presently set default cookies from the request.
     * @since 1.0
     */
    public List<Cookie> getDefaultCookies(HttpServletRequest request)
    {
        final List<Cookie>cookies = new LinkedList<>();
        final Map<String,Cookie> cookieMap = getCookieMap(request);
        for(final DefaultCookie defaultCookie : defaultCookies)
        {
            final String name = defaultCookie.getCookie().getName();
            final boolean found = cookieMap.containsKey(name);
            LOGGER.trace(String.format("Default cookie %s found? %b",name,found));
            if (found)
            {
                final Cookie cookie = cookieMap.get(name);
                cookies.add(cookieMap.get(name));
                LOGGER.trace(String.format("Adding cookie to list:\n%s", cookieUtils.cookieToString(cookie)));
            }
        }
        LOGGER.debug(String.format("Returning %d cookies.", cookies.size()));
        return cookies;
    }

    /**
     * Gets the Default cookie from the request.
     * @param name Name of the default cookie to get.
     * @param request HttpServletRequest to get cookies from.
     * @return Cookie from request, or null if it is not present.
     * @since 1.0
     */
    public Cookie getDefaultCookie(String name, HttpServletRequest request)
    {
        Cookie cookie = null;
        final Map<String,Cookie> cookieMap = getCookieMap(request);
        if(validateDefaultCookieName(name) && validateDefaultCookieIsPresent(name,cookieMap)) cookie = cookieMap.get(name);
        LOGGER.debug(String.format("Cookie %s present? %b",name, null != cookie));
        return cookie;
    }

    private boolean validateDefaultCookieName(String name)
    {
        final boolean isDefaultCookieName = defaultCookieNames.contains(name);
        LOGGER.trace(String.format("%s default cookie name? %b",name, isDefaultCookieName));
        return isDefaultCookieName;
    }

    private boolean validateDefaultCookieIsPresent(String name, Map<String,Cookie> cookieMap )
    {
        final boolean isPresent = cookieMap.containsKey(name);
        LOGGER.trace(String.format("%s default cookie is in request? %b",name, isPresent));
        return isPresent;

    }
    private Map<String,Cookie>getCookieMap(HttpServletRequest request)
    {
        final Map<String,Cookie> cookieMap = new HashMap<>();
        final Cookie[]cookies = request.getCookies();
        if(null != cookies)
        {
            for(final Cookie cookie : cookies)
            {
                cookieMap.put(cookie.getName(),cookie);
                LOGGER.trace(String.format("Adding cookie to mapping: %s", cookieUtils.cookieToString(cookie)));
            }
        }
        return cookieMap;
    }
}