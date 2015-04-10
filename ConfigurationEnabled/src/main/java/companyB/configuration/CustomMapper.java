package companyB.configuration;


import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class generates family mappings from a configuration file.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class CustomMapper
{
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomMapper.class);

    /**
     *  Returns a mapping of Family mappings based on the properties provided.
     * @param properties Key=Value parings from java properties file.
     * @return Family mappings from the properties mapping.
     * @since 1.0
     */
    public Map<String,Map<String,String>>getMappings(Map<String,String> properties)
    {
        Validate.notNull(properties);
        final Map<String,Map<String,String>> mappings = new HashMap<>();
        for(String property : properties.keySet())
        {
            final int index = property.lastIndexOf(".");
            final String family = property.substring(0,index);
            final String key = property.substring(index).replace(".","");
            final String value = properties.get(property);
            if(!mappings.containsKey(family))
            {
                mappings.put(family, new HashMap<String, String>());
            }
            mappings.get(family).put(key,value);
            LOGGER.trace(String.format("Associated key '%s' = '%s' (family: '%s')",key,value,family));
        }
        return mappings;
    }
}
