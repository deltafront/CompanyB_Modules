package companyB.http.session;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class DefaultSessionUtils
{
    private final DefaultSessionAttributes defaultSessionAttributes;
    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultSessionUtils.class);

    public DefaultSessionUtils(DefaultSessionAttributes defaultSessionAttributes)
    {
       Validate.notNull(defaultSessionAttributes);
       this.defaultSessionAttributes = defaultSessionAttributes;
    }

    public Object getDefaultSessionAttribute(HttpServletRequest request, String sessionAttribute, boolean remove)
    {
        verifySessionAttribute(sessionAttribute);
        final HttpSession session = request.getSession();
        Validate.notNull(session);
        final Object value = session.getAttribute(sessionAttribute);
        if(remove)
        {
            session.removeAttribute(sessionAttribute);
        }
        LOGGER.trace(String.format("Returning value of '%s' to client: %s (removed? %b)",sessionAttribute,value,remove));
        return value;
    }

    public boolean setDefaultSessionAttribute(HttpServletRequest request, String defaultSessionAttribute,
                                              Object defaultSessionAttributeValue, boolean initializeIfNoSession,
                                              boolean setMaxInterval)
    {
        verifySessionAttribute(defaultSessionAttribute);
        final HttpSession session = initializeIfNoSession ? request.getSession(true) : request.getSession();
        Validate.notNull(session);
        if(setMaxInterval)
        {
            session.setMaxInactiveInterval(defaultSessionAttributes.maxInterval);
        }
        session.setAttribute(defaultSessionAttribute,defaultSessionAttributeValue);
        LOGGER.trace(String.format("Setting Session Value '%s' to %s. " +
                "Initializing new session? %b Setting max interval to %d? %b",
                defaultSessionAttribute,defaultSessionAttributeValue,initializeIfNoSession,defaultSessionAttributes.maxInterval,
                setMaxInterval));
        return true;
    }

    private void verifySessionAttribute(String sessionAttribute)
    {
        Validate.isTrue(defaultSessionAttributes.defaultSessionAttributeNames.contains(sessionAttribute));
    }
}
