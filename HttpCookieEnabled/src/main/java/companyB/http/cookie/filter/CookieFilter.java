package companyB.http.cookie.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public interface CookieFilter
{
    final static Logger LOGGER = LoggerFactory.getLogger(CookieFilter.class);
    public List<Cookie>filter(Cookie[]cookies);
}
