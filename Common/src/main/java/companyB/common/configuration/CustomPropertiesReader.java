package companyB.common.configuration;

import companyB.common.utils.PropertiesUtils;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * Processes a java properties file (both standard and XML format) and returns a Mapping of all of the properties within.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 */
public class CustomPropertiesReader
{
    private final PropertiesUtils propertiesUtils = new PropertiesUtils();
    /**
     * Reads the properties file and returns a mapping of all of the properties. This mapping is still raw, meaning that
     * no contextual inferences will have been made at this point.
     * @param filename Properties file name.
     * @return Mapping of all properties in file.
     * @since 1.0.0
     */
    public Map<String,String> read(String filename)
    {
        Validate.notBlank(filename,"Properties File Name must be supplied.");
        return propertiesUtils.getProperties(filename);
    }
}
