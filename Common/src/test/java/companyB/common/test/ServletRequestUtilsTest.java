package companyB.common.test;

import companyB.common.utils.ServletRequestUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletRequest;
import java.io.*;

@Test(groups = {"unit", "common", "http.servlet.request.utils","utils"})
public class ServletRequestUtilsTest extends TestBase
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
            final String result = servletRequestUtils.getBodyFromRequest(request);
            validateNotNull(result);
            validateEquality("foo",result);
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
            final String result = servletRequestUtils.getBodyFromRequest(request);
            validateNull(result);
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
            final String result = servletRequestUtils.getBodyFromRequest(request);
            validateTrue(null == result || 0 == result.length());
        }
        finally
        {
           control.verify();
        }
    }
    private void setUpRequest(final String content)
    {
        final InputStream inputStream  = new ByteArrayInputStream(content.getBytes());
        final Reader reader =  new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        request = control.createMock(ServletRequest.class);
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
