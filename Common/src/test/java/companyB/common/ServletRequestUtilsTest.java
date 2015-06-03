package companyB.common;

import companyB.common.utils.ServletRequestUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletRequest;
import java.io.*;

import static org.testng.AssertJUnit.*;

@Test(groups = {"unit", "common", "http.servlet.request.utils"})
public class ServletRequestUtilsTest
{
    private ServletRequest request;
    private IMocksControl control;
    private ServletRequestUtils servletRequestUtils;

    @BeforeMethod
    public void before()
    {
        control = EasyMock.createControl();
        servletRequestUtils = new ServletRequestUtils();
    }

    public void happyPath()
    {
        try
        {
            setUpRequest("foo");
            String result = servletRequestUtils.getBodyFromRequest(request);
            assertNotNull(result);
            assertEquals("foo", result);
        }
        finally
        {
            control.verify();
        }
    }
    public void noContent()
    {
        try
        {
            setUpRequest("");
            String result = servletRequestUtils.getBodyFromRequest(request);
            assertNull(result);
        }
        finally
        {
            control.verify();
        }
    }
    public void exceptionThrown() throws IOException
    {
        request = control.createMock(ServletRequest.class);
        try
        {
            EasyMock.expect(request.getReader()).andThrow(new IOException(""));
            control.replay();
            String result = servletRequestUtils.getBodyFromRequest(request);
            assertTrue(null == result || 0 == result.length());
        }
        finally
        {
           control.verify();
        }
    }
    private void setUpRequest(final String content)
    {
        request = control.createMock(ServletRequest.class);
        InputStream inputStream  = new ByteArrayInputStream(content.getBytes());
        Reader reader =  new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        try
        {
            EasyMock.expect(request.getReader()).andReturn(bufferedReader);
            control.replay();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
