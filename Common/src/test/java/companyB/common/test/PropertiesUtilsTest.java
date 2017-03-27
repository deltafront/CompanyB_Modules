package companyB.common.test;

import companyB.common.utils.PropertiesUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.fail;

@Test(groups = {"unit","properties.utils","common"})
public class PropertiesUtilsTest extends TestBase
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

    @DataProvider(name = "default")
    public static Object[][]data()
    {
        return new Object[][]
                {
                        {"1",old_prop_file_name,"one"},
                        {"2",old_prop_file_name,"two"},
                        {"3",old_prop_file_name,"three"},
                        {"4",old_prop_file_name,"four"},
                        {"10",new_prop_file_name,"one"},
                        {"20",new_prop_file_name,"two"},
                        {"30",new_prop_file_name,"three"},
                        {"40",new_prop_file_name,"four"},
                        {"1",xml_prop_file,"one"},
                        {"2",xml_prop_file,"two"},
                        {"3",xml_prop_file,"three"}
                };
    }
    @DataProvider(name = "invalid")
    public static Object[][]invalid()
    {
        return new Object[][]
                {
                        {null, "five"},
                        {"", "five"},
                };
    }
    @Test(dataProvider = "default")
    public void happyPath(String expected, String filename, String key)
    {
        validateEquality(expected,propertiesUtils.getProperty(filename,key));
    }
    @Test(dataProvider = "invalid",expectedExceptions = IllegalStateException.class)
    public void withExceptions(String filename, String key)
    {
        validateNull(propertiesUtils.getProperty(filename,key));
        fail("IllegalStateException expected!");
    }
    @Test(dataProvider = "invalid")
    public void nullProperties(String path, String key)
    {
        validateNull(propertiesUtils.getProperties(path));
    }
    public void invalidProperty()
    {
        validateNull(propertiesUtils.getProperty(old_prop_file_name, "five"));
        validateInEquality("1",propertiesUtils.getProperty(new_prop_file_name, "one"));
    }

    public void nullProperty()
    {
        validateNull(propertiesUtils.getProperty(old_prop_file_name, null));
    }

    public void emptyStringProperty()
    {
        validateNull(propertiesUtils.getProperty(old_prop_file_name, ""));
    }

    
    public void getPropertiesByPath()
    {
        final Map<String, String> props_old = propertiesUtils.getProperties(old_prop_file_name);
        final Map<String, String> props_new = propertiesUtils.getProperties(new_prop_file_name);
        validateNotNull(props_old);
        validateNotNull(props_new);
        validateEquality(old_props.length, props_old.size());
        validateEquality(new_props.length, props_new.size());
        Arrays.asList(old_props).forEach((key_value_pair)->validateTrue(matcher(key_value_pair, props_old)));
        Arrays.asList(new_props).forEach((key_value_pair)->validateTrue(matcher(key_value_pair, props_new)));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getPropertiesByPathInvalidPath()
    {
        validateNull(propertiesUtils.getProperties(new_prop_file_name + ".properties"));
        fail("IllegalStateException expected - invalid file path.");
    }

    
    public void getPropertiesByPathNullPath()
    {
        validateNull(propertiesUtils.getProperties(null));
    }

    
    public void getPropertiesByPathEmptyStringPath()
    {
        validateNull(propertiesUtils.getProperties(""));
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
