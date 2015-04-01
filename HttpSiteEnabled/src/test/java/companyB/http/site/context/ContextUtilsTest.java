package companyB.http.site.context;

import companyB.http.site.IsoLang;
import companyB.http.site.IsoLocale;
import companyB.http.site.Site;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.easymock.EasyMock.anyObject;
import static org.junit.Assert.assertNull;

@Test(groups = {"unit","context.utils","http.session.enabled"})
public class ContextUtilsTest
{
    private HttpSession session;
    private IMocksControl control;


    public void setContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);

        Context context = setUpContext();
        session.setAttribute(String.valueOf(anyObject()),anyObject());
        EasyMock.expectLastCall();
        control.replay();

        ContextUtils.wrapContext(session,context);
        control.verify();
    }

    public void getContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);

        Context context = setUpContext();
        EasyMock.expect(session.getAttribute("TestContext")).andReturn(context);
        control.replay();

        Context fromSession = ContextUtils.unwrapContext("TestContext",session);
        assertNotNull(fromSession);
        assertEquals(fromSession.getContextAttributeName(),context.getContextAttributeName());
        assertEquals(fromSession.getPageId(),context.getPageId());
        assertEquals(fromSession.getOperation(),context.getOperation());
        Timestamp start = fromSession.getOperationStart();
        Timestamp end = fromSession.endOp();
        assertNotNull(start);
        assertNotNull(end);
        assertTrue(start.before(end));
        assertEquals(fromSession.getOperationEnd(), end);
        assertNotNull(fromSession.getSite());
        assertEquals(fromSession.getSite(), context.getSite());
        assertTrue(fromSession.getDuration() > 0);

        end = fromSession.endOp();
        assertNotNull(end);
        assertTrue(start.before(end));
        assertEquals(fromSession.getOperationEnd(), end);
        Set<String>keys = context.getReferences();
        assertNotNull(keys);
        for(String key : keys)
        {
            assertNotNull(context.get(key));
        }
        control.verify();
    }


    public void getStringContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);

        EasyMock.expect(session.getAttribute("TestContext")).andReturn("Bar");
        control.replay();

        Context fromSession = ContextUtils.unwrapContext("TestContext",session);
        assertNull(fromSession);
        control.verify();
    }


    public void getNullContext()
    {
        control = EasyMock.createControl();
        session = control.createMock(HttpSession.class);

        EasyMock.expect(session.getAttribute("TestContext")).andReturn(null);
        control.replay();

        Context fromSession = ContextUtils.unwrapContext("TestContext",session);
        assertNull(fromSession);
        control.verify();
    }


    private Context setUpContext()
    {
        Site site = new Site("Foo","123", IsoLang.English, null, IsoLocale.United_States);
        Context context = new Context("index.html","login",site,"TestContext");
        context.setObject("foo","bar");
        return context;
    }

}
