package companyB.http.session;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utilities for accessing default session attributes.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class DefaultSessionUtils
{
    private final DefaultSessionAttributes defaultSessionAttributes;
    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultSessionUtils.class);

    /**
     * Default constructor.
     * @param defaultSessionAttributes Container holding all default session attributes.
     * @since 1.0
     */
    public DefaultSessionUtils(DefaultSessionAttributes defaultSessionAttributes)
    {
       Validate.notNull(defaultSessionAttributes);
       this.defaultSessionAttributes = defaultSessionAttributes;
    }

    /**
     * Gets the value of the default session attribute.
     * @param request HttpServletRequest that contains the session.
     * @param sessionAttribute Session attribute to get.
     * @param remove if this is True, then the attribute will be removed from the session.
     * @return Value of session attribute.
     * @since 1.0
     */
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

    /**
     * Sets the default session attribute.
     * @param request HttpServletRequest that contains the session.
     * @param defaultSessionAttribute Session attribute to be set.
     * @param defaultSessionAttributeValue Value that is to be set.
     * @param initializeIfNoSession If true, if there is no session present, one will be initialized.
     * @param setMaxInterval If this is true, the max interval session value will be set.
     * @return True if set, false if not set (because this is not a valid default session attribute).
     */
    public boolean setDefaultSessionAttribute(HttpServletRequest request, String defaultSessionAttribute,
                                              Object defaultSessionAttributeValue, boolean initializeIfNoSession,
                                              boolean setMaxInterval)
    {
        verifySessionAttribute(defaultSessionAttribute);
        final HttpSession session = initializeIfNoSession ? request.getSession(true) : request.getSession();
        Validate.notNull(session);
        if(setMaxInterval)
        {
            session.setMaxInactiveInterval(defaultSessionAttributes.getMaxInterval());
        }
        session.setAttribute(defaultSessionAttribute,defaultSessionAttributeValue);
        LOGGER.trace(String.format("Setting Session Value '%s' to %s. " +
                "Initializing new session? %b Setting max interval to %d? %b",
                defaultSessionAttribute,defaultSessionAttributeValue,initializeIfNoSession,defaultSessionAttributes.getMaxInterval(),
                setMaxInterval));
        return true;
    }

    private void verifySessionAttribute(String sessionAttribute)
    {
        Validate.isTrue(defaultSessionAttributes.getDefaultSessionAttributeNames().contains(sessionAttribute));
    }
}
