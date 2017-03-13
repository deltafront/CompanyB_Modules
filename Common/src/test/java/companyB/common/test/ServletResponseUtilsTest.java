package companyB.common.test;

import companyB.common.utils.ServletResponseUtils;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Test(groups = {"unit", "common", "servlet.response.utils","utils"})
public class ServletResponseUtilsTest extends TestBase
{
    private IMocksControl control;
    private ServletResponse response;
    private ServletOutputStream outputStream;
    private ServletResponseUtils responseUtils;
    private String outputContent;
    @BeforeMethod
    public void before()
    {
        responseUtils = new ServletResponseUtils();
        outputContent = "";
        control = EasyMock.createControl();
        outputStream = new ServletOutputStream()
        {
            @Override
            public void write(int b) throws IOException
            {
                if(Integer.MIN_VALUE == b)throw new IOException("");
                else outputContent += new String(new byte[]{Byte.valueOf(String.valueOf(b))});
            }
        };

        response = control.createMock(ServletResponse.class);
        try
        {
            EasyMock.expect(response.getOutputStream()).andReturn(outputStream);
            control.replay();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void happyPath()
    {
        try
        {
            final String content = "foo";
            responseUtils.writeResponse(response,content,true);
            validateEquality(outputContent.length(),content.length());
            validateEquality(content,outputContent);
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
            responseUtils.writeResponse(response,null,false);
            validateEquality(0,outputContent.length());
        }
        finally
        {
            control.verify();
        }

    }
    public void exceptionThrown() throws IOException
    {
        control = EasyMock.createControl();
        response = control.createMock(ServletResponse.class);
        EasyMock.expect(response.getOutputStream()).andThrow(new IOException(""));
        control.replay();
        try
        {
            final String content = "foo";
            responseUtils.writeResponse(response,content,true);
            validateEquality(0,outputContent.length());
        }
        finally
        {
            control.verify();
        }

    }
}
