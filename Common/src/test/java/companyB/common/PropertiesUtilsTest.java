package companyB.common;

import companyB.common.utils.PropertiesUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;


public class PropertiesUtilsTest
{
    private static String[] old_props = new String[]{"one=1", "two=2", "three=3", "four=4"};
    private static String[] new_props = new String[]{"one=10", "two=20", "three=30", "four=40", "five=50"};
    private static String old_prop_file_name = "old.properties";
    private static String new_prop_file_name = "new.properties";

    @BeforeClass
    public static void beforeClass()
    {
        writeToFile(old_props, old_prop_file_name);
        writeToFile(new_props, new_prop_file_name);
    }

    @Test
    public void happyPath()
    {
        assertEquals("1", PropertiesUtils.getProperty(old_prop_file_name, "one"));
        assertEquals("2", PropertiesUtils.getProperty(old_prop_file_name, "two"));
        assertEquals("3", PropertiesUtils.getProperty(old_prop_file_name, "three"));
        assertEquals("4", PropertiesUtils.getProperty(old_prop_file_name, "four"));
        assertEquals("10", PropertiesUtils.getProperty(new_prop_file_name, "one"));
        assertEquals("20", PropertiesUtils.getProperty(new_prop_file_name, "two"));
        assertEquals("30", PropertiesUtils.getProperty(new_prop_file_name, "three"));
        assertEquals("40", PropertiesUtils.getProperty(new_prop_file_name, "four"));
    }

    @Test
    public void invalidProperty()
    {
        assertNull(PropertiesUtils.getProperty(old_prop_file_name, "five"));
        assertFalse(PropertiesUtils.getProperty(new_prop_file_name, "one").equals("1"));
    }

    @Test(expected = IllegalStateException.class)
    public void invalidFile()
    {
        assertNull(PropertiesUtils.getProperty(old_prop_file_name + ".props", "five"));
    }

    @Test(expected = IllegalStateException.class)
    public void nullFileName()
    {
        assertNull(PropertiesUtils.getProperty(null, "five"));
    }

    @Test(expected = IllegalStateException.class)
    public void emptyStringName()
    {
        assertNull(PropertiesUtils.getProperty("", "five"));
    }

    @Test
    public void nullProperty()
    {
        assertNull(PropertiesUtils.getProperty(old_prop_file_name, null));
    }

    @Test
    public void emptyStringProperty()
    {
        assertNull(PropertiesUtils.getProperty(old_prop_file_name, ""));
    }

    @Test
    public void getPropertiesByPath()
    {
        Map<String, String> props_old = PropertiesUtils.getProperties(old_prop_file_name);
        Map<String, String> props_new = PropertiesUtils.getProperties(new_prop_file_name);
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

    @Test(expected = IllegalStateException.class)
    public void getPropertiesByPathInvalidPath()
    {
        assertNull(PropertiesUtils.getProperties(new_prop_file_name + ".properties"));
    }

    @Test
    public void getPropertiesByPathNullPath()
    {
        assertNull(PropertiesUtils.getProperties(null));
    }

    @Test
    public void getPropertiesByPathEmptyStringPath()
    {
        assertNull(PropertiesUtils.getProperties(""));
    }

    private boolean matcher(String value_key_pair, Map<String, String> map)
    {
        boolean match = false;
        String[] split = value_key_pair.split("=");
        String key = split[0];
        String value = split[1];
        match = (value.equals(map.get(key)));
        if (!match)
        {
            System.out.println(key + "@" + value + "::" + map.get(key));
        }
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
