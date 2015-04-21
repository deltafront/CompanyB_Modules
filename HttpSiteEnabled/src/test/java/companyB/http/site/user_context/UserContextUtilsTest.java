package companyB.http.site.user_context;

import companyB.http.site.IsoLang;
import companyB.http.site.IsoLocale;
import companyB.http.site.Site;
import companyB.http.site.context.Context;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.easymock.EasyMock.anyObject;
import static org.junit.Assert.assertNull;

@Test(groups = {"unit","user.context.utils","http.site.enabled"})
public class UserContextUtilsTest
{

    private IMocksControl control;
    private HttpSession session;
    private UserContextUtils userContextUtils;
    @BeforeMethod
    public void before()
    {
        userContextUtils = new UserContextUtils();
    }


    public void setUserContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);
        session.setAttribute(String.valueOf(anyObject()), anyObject());
        EasyMock.expectLastCall();
        control.replay();

        userContextUtils.wrapContext(session,setUpContext());
        control.verify();
    }


    public void getUserContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);
        UserContext userContext = setUpContext();
        EasyMock.expect(session.getAttribute(UserContext.USER_CONTEXT_IDENTIFIER)).andReturn(userContext);
        control.replay();

        UserContext fromSession = userContextUtils.unwrapContext(session);
        assertNotNull(fromSession);
        assertEquals(userContext.getUserId(), fromSession.getUserId());
        assertNotNull(fromSession.getUserActivities());
        assertEquals(userContext.getUserActivities(),fromSession.getUserActivities());
        control.verify();
    }


    public void getStringUserContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);
        EasyMock.expect(session.getAttribute(UserContext.USER_CONTEXT_IDENTIFIER)).andReturn("Bar");
        control.replay();

        UserContext fromSession = userContextUtils.unwrapContext(session);
        assertNull(fromSession);
        control.verify();
    }


    public void getNullUserContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);
        EasyMock.expect(session.getAttribute(UserContext.USER_CONTEXT_IDENTIFIER)).andReturn(null);
        control.replay();

        UserContext fromSession = userContextUtils.unwrapContext(session);
        assertNull(fromSession);
        control.verify();
    }
    private UserContext setUpContext()
    {
        Site site = new Site("Foo","123", IsoLang.English, null, IsoLocale.United_States);
        Context context = new Context("index.html","login",site,"TestContext");
        context.endOp();
        UserContext userContext = new UserContext("test.user");
        userContext.addActivity(context);
        return userContext;
    }

}
