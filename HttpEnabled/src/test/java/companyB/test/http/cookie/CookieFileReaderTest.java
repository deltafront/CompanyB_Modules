package companyB.test.http.cookie;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {"unit","cookie.file.reader","http.cookie.enabled"})
public class CookieFileReaderTest extends CookieReaderTestBase
{

    @BeforeMethod
    public void before()
    {
         name = "foo";
         value = "bar";
         domain = "companyB";
         maxAge = "10";
         path = "./local";
         secure = "true";
         version = "0";
         comment = "This is a test cookie.";
         httpOnly = "true";
         boolVals = new String[]{"true","false","TRUE","FALSE"};
        deleteFileAfterWriting = false;
    }
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
    public void missingFile()
    {
        deleteFileAfterWriting = true;
        doTest(true);
    }
}
