package companyB.http.session.test;

import companyB.http.session.DefaultSessionAttributes;
import companyB.http.session.DefaultSessionAttributesReader;
import junit.framework.TestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static junit.framework.TestCase.*;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","default.session.attributes","http.session.enabled"})
public class DefaultSessionAttributesReaderTest
{
    private DefaultSessionAttributes defaultSessionAttributes;
    private String filename;
    private DefaultSessionAttributesReader defaultSessionAttributesReader;

    @BeforeMethod
    public void before()
    {
        defaultSessionAttributesReader = new DefaultSessionAttributesReader();
    }


    public void invalidFile()
    {
        defaultSessionAttributes = defaultSessionAttributesReader.readDefaultSessionAttributes("foo.props");
        assertNull(defaultSessionAttributes);
    }

    public void noMaxIntervalSingleArg()
    {
        String[]args = new String[]{"foo"};
        Integer maxInterval = null;
        filename = writeFile(maxInterval,args);
        defaultSessionAttributes = defaultSessionAttributesReader.readDefaultSessionAttributes(filename);
        verifyDefaultSessionAttributes(defaultSessionAttributes,maxInterval,args);
    }

    public void noMaxIntervalMultipleArgs()
    {
        String[]args = new String[]{"foo","bar","bat"};
        Integer maxInterval = null;
        filename = writeFile(maxInterval,args);
        defaultSessionAttributes = defaultSessionAttributesReader.readDefaultSessionAttributes(filename);
        verifyDefaultSessionAttributes(defaultSessionAttributes,maxInterval,args);
    }


    public void maxIntervalSingleArg()
    {
        String[]args = new String[]{"foo"};
        Integer maxInterval = 42;
        filename = writeFile(maxInterval,args);
        defaultSessionAttributes = defaultSessionAttributesReader.readDefaultSessionAttributes(filename);
        verifyDefaultSessionAttributes(defaultSessionAttributes,maxInterval,args);
    }

    public void maxIntervalMultipleArgs()
    {
        String[]args = new String[]{"foo","bar","bat"};
        Integer maxInterval = 42;
        filename = writeFile(maxInterval,args);
        defaultSessionAttributes = defaultSessionAttributesReader.readDefaultSessionAttributes(filename);
        verifyDefaultSessionAttributes(defaultSessionAttributes,maxInterval,args);
    }
    private String writeFile(Integer maxInterval, String...attributes)
    {
        String filename = null;
        try
        {
            StringBuilder stringBuilder = new StringBuilder("");
            if(null != maxInterval)
            {
                stringBuilder.append(String.format("%d=",maxInterval));
            }
            for(String attribute : attributes)
            {
                stringBuilder.append(String.format("%s,", attribute));
            }
            stringBuilder.trimToSize();
            String out = stringBuilder.toString();
            if(out.contains(","))
            {
                out = out.substring(0,out.lastIndexOf(","));
            }
            File file = File.createTempFile("temp","properties");
            Writer writer = new FileWriter(file);
            writer.write(out);
            writer.close();
            file.deleteOnExit();
            filename = file.getAbsolutePath();
            System.out.println(String.format("Written '%s' to '%s'.",out,filename));
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        return filename;
    }
    private void verifyDefaultSessionAttributes(DefaultSessionAttributes defaultSessionAttributes, Integer maxInterval, String...attributes)
    {
        assertNotNull(defaultSessionAttributes);
        List<String>names = defaultSessionAttributes.getDefaultSessionAttributeNames();
        assertNotNull(names);
        Integer fromInstance = defaultSessionAttributes.getMaxInterval();
        assertNotNull(fromInstance);
        if(null == maxInterval)
        {
            assertEquals(new Integer(-1),fromInstance);
        }
        else
        {
            TestCase.assertEquals(maxInterval,fromInstance);
        }
        for(String attribute : attributes)
        {
            assertTrue(names.contains(attribute));
        }
    }
}
