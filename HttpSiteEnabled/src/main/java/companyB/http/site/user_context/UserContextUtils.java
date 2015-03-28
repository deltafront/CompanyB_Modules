package companyB.http.site.user_context;

import com.google.gson.Gson;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * Utility for wrapping / unwrapping User Contexts from Http Sessions.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class UserContextUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(UserContextUtils.class);

    /**
     * Wraps this context in an HttpSession.
     * @param session Session that this context is to be wrapped in.
     * @param userContext UserContext to wrap into session.
     * @since 1.0
     */
    public static <T extends UserContext> void wrapContext(HttpSession session, final T userContext)
    {
        Validate.notNull(session);
        String string = new Gson().toJson(userContext);
        LOGGER.trace(String.format("Wrapping Context to key '%s'\n%s", UserContext.USER_CONTEXT_IDENTIFIER, string));
        session.setAttribute(UserContext.USER_CONTEXT_IDENTIFIER, userContext);
    }


    /**
     * @param session Session that context is to be unwrapped from.
     * @return UserContext from HttpSession.
     * @since 1.0
     */
    public static UserContext unwrapContext(HttpSession session)
    {
        Validate.notNull(session);
        UserContext context = null;
        Object o_context = session.getAttribute(UserContext.USER_CONTEXT_IDENTIFIER);
        if(null != o_context && o_context instanceof UserContext)
        {
            context = (UserContext)o_context;
            LOGGER.trace(String.format("Unwrapped UserContext from key '%S'\n%s",
                    UserContext.USER_CONTEXT_IDENTIFIER, new Gson().toJson(context)));
        }
        return context;
    }
}