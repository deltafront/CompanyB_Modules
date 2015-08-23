package companyB.http.cookie;

import companyB.cache.ExternalCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Enables tha writing of generated cookies to HttpServletResponses. Optionally, the values can be cached, and a key
 * supplied as a value to the cookie for value lookup.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.2.0
 */
public class CookieWriter
{
    private final ExternalCache<String,String> externalCache;
    private final static Logger LOGGER = LoggerFactory.getLogger(CookieWriter.class);

    /**
     * Default constructor. Using this means that values will be written directly to the cookie.
     */
    public CookieWriter()
    {
        this(null);
    }

    /**
     * Using is constructor means tha lookup keys will be written as values to the cookies. The 'true' values will then
     * be fetched from the external cache.
     * @param externalCache External cache to be used.
     * @since 2.2.0
     */
    public CookieWriter(ExternalCache<String,String>externalCache)
    {
        this.externalCache = externalCache;
    }

    /**
     * Writes a cookie to the response.
     * @param cookie Cookie to be written to the response.
     * @param response HttpServletResponse to write cookie to.
     * @since 2.2.0
     */
    public void writeCookie(Cookie cookie, HttpServletResponse response)
    {
        if(null == externalCache) response.addCookie(cookie);
        else externalCache.insert(getKey(cookie),cookie.getValue());
        LOGGER.trace(String.format("Cookie '%s'(value = '%s') written.",cookie.getName(),cookie.getValue()));
    }
    private String getKey(Cookie cookie)
    {
        String name  = (StringUtils.isBlank(cookie.getName())) ? "" : cookie.getName();
        String domain  = (StringUtils.isBlank(cookie.getDomain())) ? "" : cookie.getDomain();
        return DigestUtils.md5Hex(String.format("%s%s",name,domain));
    }
}
