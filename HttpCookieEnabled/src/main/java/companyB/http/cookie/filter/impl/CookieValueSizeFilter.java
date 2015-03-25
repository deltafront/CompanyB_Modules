package companyB.http.cookie.filter.impl;

import companyB.http.cookie.filter.CookieFilter;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class CookieValueSizeFilter implements CookieFilter
{
    private final int maxSize;

    public CookieValueSizeFilter(int maxSize)
    {
        this.maxSize = maxSize;
    }

    @Override
    public List<Cookie> filter(List<Cookie> cookies)
    {
        final List<Cookie>cookieList = new LinkedList<>();
        for(Cookie cookie : cookies)
        {
            int fromCookie = cookie.getValue().getBytes().length;
            final boolean matches = fromCookie <= maxSize;
            if(matches)
            {
                cookieList.add(cookie);
            }
            LOGGER.trace(String.format("Byte size from cookie (%d) less than max size (%d) ? %b"
                    ,fromCookie,maxSize,matches));
        }
        LOGGER.trace(String.format("Returning %d cookies.", cookieList.size()));
        return cookieList;
    }
}
