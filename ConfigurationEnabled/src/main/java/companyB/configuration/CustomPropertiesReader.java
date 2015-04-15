package companyB.configuration;

import org.apache.commons.lang3.Validate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Processes a java properties file (both standard and XML format) and returns a Mapping of all of the properties within.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class CustomPropertiesReader
{
    /**
     * Reads the properties file and returns a mapping of all of the properties. This mapping is still raw, meaning that
     * no contextual inferences will have been made at this point.
     * @param filename Properties file name.
     * @return Mapping of all properties in file.
     * @since 1.0
     */
    public Map<String,String> read(String filename)
    {
        Validate.notBlank(filename,"Properties File Name must be supplied.");
        final Map<String,String>props = new HashMap<>();
        final File file = new File(filename);
        try
        {
            Reader reader = new FileReader(file);
            final Properties properties = new Properties();
            if(filename.toLowerCase().contains(".xml"))
            {
                properties.loadFromXML(new FileInputStream(file));
            }
            else
            {
                properties.load(reader);
            }
            final Set<String> names = properties.stringPropertyNames();
            for(String name : names)
            {
                props.put(name,properties.getProperty(name));
            }
        }
        catch (IOException e)
        {
          e.printStackTrace(System.err);
        }
        return props;
    }
}
