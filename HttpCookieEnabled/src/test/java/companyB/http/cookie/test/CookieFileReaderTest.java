package companyB.http.cookie.test;

import companyB.http.cookie.CookieFileReader;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test(groups = {"unit","cookie.file.reader","http.cookie.enabled"})
public class CookieFileReaderTest extends CookieReaderTestBase
{

    public void allValues()
    {
        doTest(true);
    }
    
    public void missingName()
    {
        name = "";
        doTest(false);
    }
    
    public void missingValue()
    {
        value = "";
        doTest(false);
    }
    
    public void missingDomain()
    {
        domain = "";
        doTest(false);
    }
    
    public void missingPath()
    {
        path = "";
        doTest(false);
    }
    
    public void missingComment()
    {
        comment = "";
        doTest(false);
    }
    
    public void missingMaxAge()
    {
        maxAge = "";
        doTest(false);
    }
    
    public void missingVersion()
    {
        version = "";
        doTest(false);
    }
    
    public void invalidVersion()
    {
        version = "2";
        doTest(false);
    }


    
    public void missingSecure()
    {
        secure = "";
        doTest(false);
    }
    
    public void invalidSecure()
    {
        secure = "yes";
        doTest(false);
    }
    
    public void missingHttpOnly()
    {
        httpOnly = "";
        doTest(false);
    }

    
    public void invalidHttpOnly()
    {
        httpOnly = "no";
        doTest(false);
    }

    public void invalidFile()
    {
        CookieFileReader cookieFileReader =  new CookieFileReader();
        List list = cookieFileReader.readCookiesFromFile("foo");
        assertNotNull(list);
        assertEquals(0,list.size());
    }
}
