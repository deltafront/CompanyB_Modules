package companyB.configuration.test;

import companyB.configuration.ConfigEnabler;
import companyB.configuration.CustomPropertiesReader;
import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chburrell on 2/13/15.
 */
public class CustomPropertiesReaderTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
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

    @Test
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