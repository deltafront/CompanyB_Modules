package companyB.http.site.user_context;

import com.google.gson.Gson;
import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpSession;

/**
 * Utility for wrapping / unwrapping User Contexts from Http Sessions.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class UserContextUtils extends UtilityBase
{
    /**
     * Wraps this context in an HttpSession.
     * @param session Session that this context is to be wrapped in.
     * @param userContext UserContext to wrap into session.
     * @since 1.0.0
     */
    public <T extends UserContext> void wrapContext(HttpSession session, final T userContext)
    {
        Validate.notNull(session,"Session to wrap UserContext in is required.");
        String string = new Gson().toJson(userContext);
        LOGGER.trace(String.format("Wrapping Context to key '%s'\n%s", UserContext.USER_CONTEXT_IDENTIFIER, string));
        session.setAttribute(UserContext.USER_CONTEXT_IDENTIFIER, userContext);
    }


    /**
     * @param session Session that context is to be unwrapped from.
     * @return UserContext from HttpSession.
     * @since 1.0.0
     */
    public UserContext unwrapContext(HttpSession session)
    {
        Validate.notNull(session,"Session to unwrap UserContext from is required.");
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
