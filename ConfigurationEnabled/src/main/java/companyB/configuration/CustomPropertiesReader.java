package companyB.configuration;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomPropertiesReader.class);
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
            if(filename.toLowerCase().contains(".xml"))properties.loadFromXML(new FileInputStream(file));
            else properties.load(reader);
            final Set<String> names = properties.stringPropertyNames();
            for(String name : names)
            {

                String value = properties.getProperty(name);
                props.put(name,value);
                LOGGER.trace(String.format("Associating '%s=>%s'.",name,value));
            }
        }
        catch (IOException e)
        {
            LOGGER.trace(e.getMessage(),e);
        }
        return props;
    }
}
