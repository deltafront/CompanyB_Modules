package companyB.http.cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains utility methods.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0
 */
public abstract class Utils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    public static String cookieToString(Cookie cookie)
    {
        StringBuilder stringBuilder = new StringBuilder("");
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
                    {
                        stringBuilder.append(String.format("\n%s = %s",name.replace(replacement,""),String.valueOf(obj_value)));
                    }
                }
                catch (InvocationTargetException | IllegalAccessException e)
                {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
        stringBuilder.trimToSize();
        return stringBuilder.toString();
    }
}
