package companyB.spring.enabled.test;

import companyB.configuration.ConfigEnabled;
import companyB.configuration.ConfigEnabler;
import companyB.spring.configurationEnabled.ConfigurationEnabledBeanProcessor;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@Test(groups = {"unit","spring.enabled","configuration.enabled.bean.processir"})
public class ConfigurationEnabledBeanProcessorTest
{
    public void testPostProcessBeforeInitialization()
    {
        boolean passed = false;
        File file = new File("config.properties");
        try
        {
            String out = "foo.int=42\nfoo.bool=true\nbar.int=43\nbar.bool=false";
            Writer writer = new FileWriter(file);
            writer.write(out);
            writer.close();
            TestClass testClass = new TestClass();
            testClass = (TestClass)new ConfigurationEnabledBeanProcessor().postProcessBeforeInitialization(testClass, "testClass");
            assertNotNull(testClass);
            assertNotNull(testClass.configEnabler_1);
            assertNotNull(testClass.configEnabler_2);
            passed = testClass.callAll();
        }
        catch (IOException e)
        {
            passed = false;
        }
        finally
        {
            if(file.exists())
            {
                file.deleteOnExit();
            }
        }
        assertTrue(passed);
    }
    @Test(expectedExceptions = NullPointerException.class)
    public void testPostProcessAfterInitialization()
    {
        File file = new File("config.properties");
        try
        {
            String out = "foo.int=42\nfoo.bool=true\nbar.int=43\nbar.bool=false";
            Writer writer = new FileWriter(file);
            writer.write(out);
            writer.close();
            TestClass testClass = new TestClass();
            testClass = (TestClass)new ConfigurationEnabledBeanProcessor().postProcessAfterInitialization(testClass, "testClass");
            assertNotNull(testClass);
            assertNull(testClass.configEnabler_1);
            assertNull(testClass.configEnabler_2);
            testClass.callAll();
            fail("Null pointer Exception expected.");
        }
        catch (IOException e)
        {}
        finally
        {
            if(file.exists())
            {
                file.deleteOnExit();
            }
        }
    }
}
class TestClass
{
    @ConfigEnabled(filename = "config.properties", family = "foo")
    public ConfigEnabler configEnabler_1;

    @ConfigEnabled(filename = "config.properties", family = "bar")
    public ConfigEnabler configEnabler_2;

    public boolean callAll()
    {
        boolean allPresent = true;
        List<Object> objects = new LinkedList<>();
        objects.add(configEnabler_1.getInteger("int"));
        objects.add(configEnabler_1.getBoolean("bool"));
        objects.add(configEnabler_2.getInteger("int"));
        objects.add(configEnabler_2.getBoolean("bool"));
        for(Object object : objects)
        {
            if(null == object)
            {
                allPresent = false;
                break;
            }
        }
        return allPresent;
    }
}
