package companyB.http.cookie;

import companyB.cache.ExternalCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Class that reads cookies from an HttpServletRequest.
 * This class can be provided with an implementation of ExternalCache so as to read values that would otherwise too big
 * or contain illegal characters for the HttpServletRequest.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.2.0
 */
public class CookieReader
{
    private final ExternalCache<String,String> externalCache;
    private final static Logger LOGGER = LoggerFactory.getLogger(CookieReader.class);

    /**
     * Default constructor. Using this constructor will disable cache reads.
     * @since 2.2.0
     */
    public CookieReader()
    {
        this(null);
    }

    /**
     * @param externalCache Implementation of ExternalCache. If this is null, cache reads will be disabled.
     * @since 2.2.0
     */
    public CookieReader(ExternalCache<String,String>externalCache)
    {
        this.externalCache = externalCache;
    }

    /**
     * Gets the value of a cookie from the request. If caching is enabled, the value is expected to be a key that will be
     * used in getting the 'real' value from the cache.
     * @param request HttpServletRequest
     * @param cookieName Name of the cookie.
     * @return Value of cookie.
     * @since 2.2.0
     */
    public String readCookie(HttpServletRequest request,String cookieName)
    {
        Validate.notBlank(cookieName,"Cookie name is required.");
        Validate.notNull(request,"HttpRequest is required.");
        String value = getCookieValue(request,cookieName);
        if (null != externalCache && StringUtils.isNotBlank(value))value = externalCache.retrieve(value);
        LOGGER.trace(String.format("Returning value for cookie '%s':'%s'.",cookieName,value));
        return value;
    }

    private String getCookieValue(HttpServletRequest request,String name)
    {
        String value = null;
        Cookie[]cookies = request.getCookies();
        if(null == cookies || 0 == cookies.length)value= null;
        else
        {
            for(Cookie cookie : cookies)
            {
                if(name.equals(cookie.getName()))
                {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
