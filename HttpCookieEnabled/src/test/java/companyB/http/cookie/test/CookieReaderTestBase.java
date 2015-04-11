package companyB.http.cookie.test;

import companyB.http.cookie.CookieFileReader;
import companyB.http.cookie.DefaultCookie;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class CookieReaderTestBase extends TestBase
{
    protected String name = "foo";
    protected String value = "bar";
    protected String domain = "companyB";
    protected String maxAge = "10";
    protected String path = "./local";
    protected String secure = "true";
    protected String version = "0";
    protected String comment = "This is a test cookie.";
    protected String httpOnly = "true";
    protected String[]boolVals = {"true","false","TRUE","FALSE"};

    protected String writeCookieFile()
    {
        String header = "#name,value,domain,maxAge,path,secure,version,comment,httpOnly";
        String content = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",name,value,domain,maxAge,path,secure,version,comment,httpOnly);
        List<String> lines = new LinkedList<>();
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
    protected void doTest(boolean cookiesExpected)
    {
        String filename = writeCookieFile();
        try
        {
            Thread.sleep(100);
            CookieFileReader cookieFileReader =  new CookieFileReader();
            List<DefaultCookie> defaultCookies = cookieFileReader.readCookiesFromFile(filename);
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
        catch (IllegalArgumentException | InterruptedException e)
        {
            if(cookiesExpected)
            {
                fail("This exception should not have been thrown.");
            }
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
}
