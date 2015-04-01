package companyB.http.session.test;

import companyB.http.session.DefaultSessionAttributes;
import companyB.http.session.DefaultSessionUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.*;

@Test(groups = {"unit","default.session.utils","http.session.enabled"})
public class DefaultSessionUtilsTest
{
    private HttpServletRequest request;
    private HttpSession session;
    private DefaultSessionAttributes sessionAttributes;
    private String attributeName = "foo";
    private Object attributeValue = 42;
    private DefaultSessionUtils sessionUtils;


    public void getValidAttributeRemoveFalse()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession()).andReturn(session);
            EasyMock.expect(session.getAttribute(attributeName)).andReturn(attributeValue);
            control.replay();
            Object value = sessionUtils.getDefaultSessionAttribute(request,attributeName,false);
            assertNotNull(value);
            assertEquals(attributeValue,value);
        }
        finally
        {
            control.verify();
        }
    }

    public void getValidAttributeRemoveTrue()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession()).andReturn(session);
            EasyMock.expect(session.getAttribute(attributeName)).andReturn(attributeValue);
            control.replay();
            Object value = sessionUtils.getDefaultSessionAttribute(request,attributeName,true);
            assertNotNull(value);
            assertEquals(attributeValue,value);

            control.reset();
            EasyMock.expect(request.getSession()).andReturn(session);
            EasyMock.expect(session.getAttribute(attributeName)).andReturn(null);
            control.replay();
            value = sessionUtils.getDefaultSessionAttribute(request,attributeName,false);
            assertNull(value);
        }
        finally
        {
            control.verify();
        }
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void getInvalidAttribute()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            control.replay();
            Object value = sessionUtils.getDefaultSessionAttribute(request,"bar",false);
            assertNotNull(value);
            assertEquals(attributeValue,value);
            fail("IllegalArgumentException should have been thrown.");
        }
        finally
        {
            control.verify();
        }
    }
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void setInvalidAttribute()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            control.replay();
            sessionUtils.setDefaultSessionAttribute(request, "bar", attributeValue, true, true);
            fail("IllegalArgumentException should have been thrown.");
        }
        finally
        {
            control.verify();
        }
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void setValidAttributeNoSessionNotSettingSession()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession()).andReturn(null);

            control.replay();
            sessionUtils.setDefaultSessionAttribute(request, attributeName, attributeValue, false, true);
            fail("IllegalArgumentException should have been thrown.");
        }
        finally
        {
            control.verify();
        }
    }

    public void setValidAttributeNoSessionSettingSession()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession(true)).andReturn(session);

            control.replay();
            boolean set = sessionUtils.setDefaultSessionAttribute(request,attributeName,attributeValue,true,true);
            assertTrue(set);
        }
        finally
        {
            control.verify();
        }
    }

    public void setValidAttributeSessionSettingMaxInterval()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession()).andReturn(session);

            control.replay();
            boolean set = sessionUtils.setDefaultSessionAttribute(request, attributeName, attributeValue, false, true);
            assertTrue(set);
        }
        finally
        {
            control.verify();
        }
    }

    public void setValidAttributeSessionNotSettingMaxInterval()
    {
        IMocksControl control = EasyMock.createNiceControl();
        try
        {
            sessionAttributes = new DefaultSessionAttributes();
            sessionAttributes.maxInterval = 100;
            sessionAttributes.defaultSessionAttributeNames = new LinkedList<>();
            sessionAttributes.defaultSessionAttributeNames.add(attributeName);
            sessionUtils = new DefaultSessionUtils(sessionAttributes);

            request = control.createMock(HttpServletRequest.class);
            session = control.createMock(HttpSession.class);

            EasyMock.expect(request.getSession()).andReturn(session);

            control.replay();
            boolean set = sessionUtils.setDefaultSessionAttribute(request, attributeName, attributeValue, false, false);
            assertTrue(set);
        }
        finally
        {
            control.verify();
        }
    }
}
