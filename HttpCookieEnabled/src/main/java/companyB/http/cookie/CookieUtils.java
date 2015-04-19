package companyB.http.cookie;

import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.Cookie;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains utility methods.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0
 */
public class CookieUtils extends UtilityBase
{

    /**
     * Prints this cookie to String.
     * @param cookie Cookie to be printed.
     * @return String representation of Cookie.
     * @since 1.0
     */
    @SuppressWarnings("ConstantConditions")
    public String cookieToString(Cookie cookie)
    {
        Validate.notNull(cookie,"Cookie must be supplied.");
        String out = "";
        Method[]methods = cookie.getClass().getMethods();
        for(Method method : methods)
        {
            String name = method.getName();
            if(0 == name.indexOf("get") || 0 == name.indexOf("is"))
            {
                String replacement = (0 == name.indexOf("get")) ? "get" : "is";
                try
                {
                    Object[]args = null;
                    Object obj_value = method.invoke(cookie,args);
                    if(null != obj_value)
                        out += String.format("\n%s = %s",name.replace(replacement,""),String.valueOf(obj_value));

                }
                catch (InvocationTargetException | IllegalAccessException e)
                {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
        return out;
    }
}
