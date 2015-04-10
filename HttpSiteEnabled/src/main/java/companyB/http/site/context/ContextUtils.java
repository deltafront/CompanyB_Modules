package companyB.http.site.context;

import com.google.gson.Gson;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;

/**
 * Utility for wrapping / unwrapping Contexts from Http Sessions.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class ContextUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ContextUtils.class);
    /**
     * Wraps this context in an HttpSession.
     * @param session Session that this context is to be wrapped in.
     * @param context Context to wrap into session.
     * @since 1.0
     */
    public <T extends Context> void wrapContext(HttpSession session, final T context)
    {
        Validate.notNull(session);
        String string = new Gson().toJson(context);
        session.setAttribute(context.getContextAttributeName(), context);
        LOGGER.trace(String.format("Wrapping Context to key '%s'\n%s", context.getContextAttributeName(), string));
    }

    /**
     * Gets the context that has been wrapped in the session.
     * @param contextAttributeName Name that this context is to be keyed to when it has been wrapped in an HttpSession.
     * @param session Session that this context has been wrapped in.
     * @return Context from Session, or null if not found.
     * @since 1.0
     */
    public <T extends Context> T unwrapContext(String contextAttributeName, HttpSession session)
    {
        Context context = null;
        Object o_context = session.getAttribute(contextAttributeName);
        if(null != o_context && o_context instanceof Context)
        {
            context = (Context)o_context;
            LOGGER.trace(String.format("Unwrapped Context from key '%s'\n%s", contextAttributeName, context.toString()));
        }
        return (T)context;
    }
}
