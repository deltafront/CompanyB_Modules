package companyB.common.test;

import companyB.common.utils.PropertiesUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
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
        assertThat(propertiesUtils.getProperty(old_prop_file_name, "one"),is(equalTo("1")));
        assertThat(propertiesUtils.getProperty(old_prop_file_name, "two"),is(equalTo("2")));
        assertThat(propertiesUtils.getProperty(old_prop_file_name, "three"),is(equalTo("3")));
        assertThat(propertiesUtils.getProperty(old_prop_file_name, "four"),is(equalTo("4")));
        assertThat(propertiesUtils.getProperty(new_prop_file_name, "one"),is(equalTo("10")));
        assertThat(propertiesUtils.getProperty(new_prop_file_name, "two"),is(equalTo("20")));
        assertThat(propertiesUtils.getProperty(new_prop_file_name, "three"),is(equalTo("30")));
        assertThat(propertiesUtils.getProperty(new_prop_file_name, "four"),is(equalTo("40")));
        assertThat(propertiesUtils.getProperty(xml_prop_file,"one"),is(equalTo("1")));
        assertThat(propertiesUtils.getProperty(xml_prop_file,"two"),is(equalTo("2")));
        assertThat(propertiesUtils.getProperty(xml_prop_file,"three"),is(equalTo("3")));
    }

    
    public void invalidProperty()
    {
        assertThat(propertiesUtils.getProperty(old_prop_file_name, "five"),is(nullValue()));
        assertThat(propertiesUtils.getProperty(new_prop_file_name, "one"),is(not(equalTo("1"))));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void invalidFile()
    {
        assertThat(propertiesUtils.getProperty(old_prop_file_name + ".props", "five"),is(nullValue()));
        fail("IllegalStateException expected - invalid file.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void nullFileName()
    {
        assertThat(propertiesUtils.getProperty(null, "five"),is(nullValue()));
        fail("IllegalStateException expected - null filename.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void emptyStringName()
    {
        assertThat(propertiesUtils.getProperty("", "five"),is(nullValue()));
        fail("IllegalStateException expected - empty string filename.");
    }

    
    public void nullProperty()
    {
        assertThat(propertiesUtils.getProperty(old_prop_file_name, null),is(nullValue()));
    }

    
    public void emptyStringProperty()
    {
        assertThat(propertiesUtils.getProperty(old_prop_file_name, ""),is(nullValue()));
    }

    
    public void getPropertiesByPath()
    {
        final Map<String, String> props_old = propertiesUtils.getProperties(old_prop_file_name);
        final Map<String, String> props_new = propertiesUtils.getProperties(new_prop_file_name);
        assertThat(props_old,is(not(nullValue())));
        assertThat(props_new,is(not(nullValue())));
        assertThat(old_props.length, is(equalTo(props_old.size())));
        assertThat(new_props.length, is(equalTo(props_new.size())));
        Arrays.asList(old_props).forEach((key_value_pair)->assertThat(matcher(key_value_pair, props_old),is(true)));
        Arrays.asList(new_props).forEach((key_value_pair)->assertThat(matcher(key_value_pair, props_new),is(true)));
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
        final String[] split = value_key_pair.split("=");
        final String key = split[0];
        final String value = split[1];
        return  value.equals(map.get(key));
    }

    private static void writeToFile(String[] inputs, String filename)
    {
        final File file = new File(filename);
        try(final FileWriter fw = new FileWriter(file);
             final BufferedWriter bw = new BufferedWriter(fw);)
        {
            for (String input : inputs)
            {
                bw.write(input);
                bw.newLine();
            }
            file.deleteOnExit();
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }
}
