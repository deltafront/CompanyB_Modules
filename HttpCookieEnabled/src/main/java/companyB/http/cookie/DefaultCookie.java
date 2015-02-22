package companyB.http.cookie;

import javax.servlet.http.Cookie;

/**
 * Top Level container for Cookies. This is intended to contain metadata regarding a specific cookie that would not be
 * able to be present in the Cookie data structure itself.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0
 */
public class DefaultCookie
{
    private final Cookie cookie;
    private final boolean replaceIfExists;

    /**
     * Default constructor.
     * @param cookie Cookie to be contained.
     * @param replaceIfExists Whether or not to replace this cookie in the context if it already exists. Defaults to false.
     * @since 1.0
     */
    public DefaultCookie(Cookie cookie, boolean replaceIfExists)
    {
        this.cookie = cookie;
        this.replaceIfExists = replaceIfExists;
    }

    /**
     * @return Cookie.
     * @since 1.0
     */
    public Cookie getCookie()
    {
        return cookie;
    }

    /**
     * @return Whether or not to replace this cookie in the context if it already exists.
     * @since 1.0
     */
    public boolean isReplaceIfExists()
    {
        return replaceIfExists;
    }
}
