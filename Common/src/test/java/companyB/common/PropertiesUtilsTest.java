package companyB.common;

import companyB.common.utils.PropertiesUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

@Test(groups = {"unit","properties.utils","common"})
public class PropertiesUtilsTest
{
    private PropertiesUtils propertiesUtils;
    private static String[] old_props = {"one=1", "two=2", "three=3", "four=4"};
    private static String[] new_props = {"one=10", "two=20", "three=30", "four=40", "five=50"};
    private static String old_prop_file_name = "old.properties";
    private static String new_prop_file_name = "new.properties";
    private static String xml_prop_file = "config.properties.xml";
    private static String[] xml_props = {
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
            "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">",
            "<properties>","<entry key='one'>1</entry>","<entry key='two'>2</entry>",
            "<entry key='three'>3</entry>","</properties>"};

    @BeforeClass
    public static void beforeClass()
    {
        writeToFile(old_props, old_prop_file_name);
        writeToFile(new_props, new_prop_file_name);
        writeToFile(xml_props, xml_prop_file);
    }
    @BeforeMethod
    public void before()
    {
        propertiesUtils = new PropertiesUtils();
    }

    public void happyPath()
    {
        assertEquals("1", propertiesUtils.getProperty(old_prop_file_name, "one"));
        assertEquals("2", propertiesUtils.getProperty(old_prop_file_name, "two"));
        assertEquals("3", propertiesUtils.getProperty(old_prop_file_name, "three"));
        assertEquals("4", propertiesUtils.getProperty(old_prop_file_name, "four"));
        assertEquals("10", propertiesUtils.getProperty(new_prop_file_name, "one"));
        assertEquals("20", propertiesUtils.getProperty(new_prop_file_name, "two"));
        assertEquals("30", propertiesUtils.getProperty(new_prop_file_name, "three"));
        assertEquals("40", propertiesUtils.getProperty(new_prop_file_name, "four"));
        assertEquals("1",propertiesUtils.getProperty(xml_prop_file,"one"));
        assertEquals("2",propertiesUtils.getProperty(xml_prop_file,"two"));
        assertEquals("3",propertiesUtils.getProperty(xml_prop_file,"three"));
    }

    
    public void invalidProperty()
    {
        assertNull(propertiesUtils.getProperty(old_prop_file_name, "five"));
        assertFalse(propertiesUtils.getProperty(new_prop_file_name, "one").equals("1"));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void invalidFile()
    {
        assertNull(propertiesUtils.getProperty(old_prop_file_name + ".props", "five"));
        fail("IllegalStateException expected - invalid file.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void nullFileName()
    {
        assertNull(propertiesUtils.getProperty(null, "five"));
        fail("IllegalStateException expected - null filename.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void emptyStringName()
    {
        assertNull(propertiesUtils.getProperty("", "five"));
        fail("IllegalStateException expected - empty string filename.");
    }

    
    public void nullProperty()
    {
        assertNull(propertiesUtils.getProperty(old_prop_file_name, null));
    }

    
    public void emptyStringProperty()
    {
        assertNull(propertiesUtils.getProperty(old_prop_file_name, ""));
    }

    
    public void getPropertiesByPath()
    {
        Map<String, String> props_old = propertiesUtils.getProperties(old_prop_file_name);
        Map<String, String> props_new = propertiesUtils.getProperties(new_prop_file_name);
        assertNotNull(props_old);
        assertNotNull(props_new);
        assertEquals(old_props.length, props_old.size());
        assertEquals(new_props.length, props_new.size());
        for (String key_value_pair : old_props)
        {
            assertTrue(matcher(key_value_pair, props_old));
        }
        for (String key_value_pair : new_props)
        {
            assertTrue(matcher(key_value_pair, props_new));
        }
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getPropertiesByPathInvalidPath()
    {
        assertNull(propertiesUtils.getProperties(new_prop_file_name + ".properties"));
        fail("IllegalStateException expected - invalid file path.");
    }

    
    public void getPropertiesByPathNullPath()
    {
        assertNull(propertiesUtils.getProperties(null));
    }

    
    public void getPropertiesByPathEmptyStringPath()
    {
        assertNull(propertiesUtils.getProperties(""));
    }

    private boolean matcher(String value_key_pair, Map<String, String> map)
    {
        String[] split = value_key_pair.split("=");
        String key = split[0];
        String value = split[1];
        boolean match = (value.equals(map.get(key)));
        return match;
    }

    private static void writeToFile(String[] inputs, String filename)
    {
        try
        {
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String input : inputs)
            {
                bw.write(input);
                bw.newLine();
            }
            bw.close();
            file.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }
}
