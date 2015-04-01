package companyB.configuration.test;

import companyB.configuration.ConfigEnabler;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Test(groups = {"unit","custom.properties.reader","configuration.enabled"})
public class CustomPropertiesReaderTest
{
    public void loadFromXml() throws Exception
    {
        String prolog = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">";
        String entry = prolog + "<properties><entry key=\"family.foo\">bar</entry></properties>";
        File file = File.createTempFile("config",".xml");
        Writer writer = new FileWriter(file);
        writer.write(entry);
        writer.close();
        file.deleteOnExit();
        String filename = file.getAbsolutePath();
        System.out.println(filename);
        ConfigEnabler configEnabler = new ConfigEnabler(filename,"family");
        String out = configEnabler.getString("foo");
        assertNotNull(out);
        assertEquals("bar",out);
    }

    public void loadFromInvalidXML()
    {
        String prolog = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">";
        String entry = prolog + "<properties><entry key=\"family.foo\">bar</entry></properties>";
        String filename = "";
        try
        {
            File file = File.createTempFile("config",".xml");
            Writer writer = new FileWriter(file);
            writer.write(entry);
            writer.close();
            file.deleteOnExit();

        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }
        try
        {
            new ConfigEnabler(filename,"family");
            fail("Exception expected.");
        }
        catch (Exception e)
        {
            boolean expected = (e instanceof IllegalArgumentException);
            assertTrue(String.format("Instance of %s caught.",e.getClass().getCanonicalName()),expected);
        }
    }
}
