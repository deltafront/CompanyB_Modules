package companyB.http.cookie.test;

import companyB.http.cookie.CookieFileReader;
import companyB.http.cookie.DefaultCookie;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class CookieFileReaderTest extends TestBase
{

    private String name = "foo";
    private String value = "bar";
    private String domain = "companyB";
    private String maxAge = "10";
    private String path = "./local";
    private String secure = "true";
    private String version = "0";
    private String comment = "This is a test cookie.";
    private String httpOnly = "true";
    private String[]boolVals = {"true","false","TRUE","FALSE"};

    //#name,value,domain,maxAge,path,secure,version,comment,httpOnly
    private String writeCookieFile()
    {
        String header = "#name,value,domain,maxAge,path,secure,version,comment,httpOnly";
        String content = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",name,value,domain,maxAge,path,secure,version,comment,httpOnly);
        List<String>lines = new LinkedList<>();
        lines.add(header);
        lines.add(content);
        String filename = null;
        try
        {
            File file = File.createTempFile("cookie","definition");
            Writer writer = new FileWriter(file);
            for(String line : lines)
            {
                writer.write(String.format("%s\n",line));
            }
            writer.close();
            filename = file.getAbsolutePath();
            file.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return filename;
    }

    @Test
    public void allValues()
    {
        doTest(true);
    }
    @Test
    public void missingName()
    {
        name = "";
        doTest(false);
    }
    @Test
    public void missingValue()
    {
        value = "";
        doTest(false);
    }
    @Test
    public void missingDomain()
    {
        domain = "";
        doTest(false);
    }
    @Test
    public void missingPath()
    {
        path = "";
        doTest(false);
    }
    @Test
    public void missingComment()
    {
        comment = "";
        doTest(false);
    }
    @Test
    public void missingMaxAge()
    {
        maxAge = "";
        doTest(false);
    }
    @Test
    public void missingVersion()
    {
        version = "";
        doTest(false);
    }
    @Test
    public void invalidVersion()
    {
        version = "2";
        doTest(false);
    }
    @Test
    public void validVersions()
    {
        version = "1";
        doTest(true);
        version = "0";
        doTest(true);
    }
    @Test
    public void missingSecure()
    {
        secure = "";
        doTest(false);
    }
    @Test
    public void invalidSecure()
    {
        secure = "yes";
        doTest(false);
    }
    @Test
    public void validSecure()
    {
        for (String val : boolVals)
        {
            secure = val;
            doTest(true);
        }
    }
    @Test
    public void missingHttpOnly()
    {
        httpOnly = "";
        doTest(false);
    }

    @Test
    public void invalidHttpOnly()
    {
        httpOnly = "no";
        doTest(false);
    }
    @Test
    public void validHttpOnly()
    {
        for (String val : boolVals)
        {
            httpOnly = val;
            doTest(true);
        }
    }


    private void verifyDefaultCookie(DefaultCookie defaultCookie)
    {
        assertNotNull(defaultCookie);
        Cookie cookie = defaultCookie.getCookie();
        assertNotNull(cookie);
        assertEquals(name,cookie.getName());
        assertEquals(value,cookie.getValue());
        assertEquals(domain.toLowerCase(),cookie.getDomain());
        assertEquals(path,cookie.getPath());
        if("".equals(comment))
        {
            assertNull(cookie.getComment());
        }
        else
        {
            assertEquals(comment,cookie.getComment());
        }
        if("".equals(version))
        {
            assertEquals(0,cookie.getVersion());
        }
        else
        {
            assertEquals(Integer.parseInt(version),cookie.getVersion());
        }
        if("".equals(maxAge))
        {
            assertEquals(-1,cookie.getMaxAge());
        }
        else
        {
            assertEquals(Integer.parseInt(maxAge),cookie.getMaxAge());
        }
        if("".equals(secure))
        {
            assertEquals(false,cookie.getSecure());
        }
        else
        {
            assertEquals(Boolean.parseBoolean(secure),cookie.getSecure());
        }
        if("".equals(httpOnly))
        {
            assertEquals(false,cookie.isHttpOnly());
        }
        else
        {
            assertEquals(Boolean.parseBoolean(httpOnly),cookie.isHttpOnly());
        }
    }
    private void doTest(boolean cookiesExpected)
    {
        String filename = writeCookieFile();
        try
        {
            List<DefaultCookie> defaultCookies = CookieFileReader.readCookiesFromFile(filename);
            assertNotNull(defaultCookies);
            if(cookiesExpected)
            {
                assertFalse(defaultCookies.isEmpty());
                for(DefaultCookie defaultCookie : defaultCookies)
                {
                    verifyDefaultCookie(defaultCookie);
                }
            }
            else
            {
               fail("Illegal Argument Exception should have been thrown.");
            }
        }
        catch (IllegalArgumentException e)
        {
            if(cookiesExpected)
            {
                fail("This exception should not have been thrown.");
            }
        }

    }





}
