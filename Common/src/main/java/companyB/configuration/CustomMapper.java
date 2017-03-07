package companyB.configuration;


import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * This class generates family mappings from a configuration file.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class CustomMapper
{
    /**
     *  Returns a mapping of Family mappings based on the properties provided.
     * @param properties Key=Value parings from java properties file.
     * @return Family mappings from the properties mapping.
     */
    public Map<String,Map<String,String>>getMappings(Map<String,String> properties)
    {
        Validate.notNull(properties);
        final Map<String,Map<String,String>> mappings = new HashMap<>();
        properties.keySet().forEach(property ->
        {
            final int index = property.lastIndexOf(".");
            final String family = property.substring(0,index);
            final String key = property.substring(index).replace(".","");
            final String value = properties.get(property);
            if(!mappings.containsKey(family))mappings.put(family, new HashMap<>());
            mappings.get(family).put(key,value);
        });
        return mappings;
    }
}
