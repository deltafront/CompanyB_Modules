package companyB.configuration;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads configurations from the indicated properties file. Each instance of ConfigEnabler will return values based upon a single properties filename / contextual family paring.
 * <ul>
 *     <li>
 *         If a different contextual family from the same properties file is needed, a separate instance of ConfigEnabler will need to be instantiated.
 For this case, however, the performance impact should be minimal, since all contextual families within a single properties file are loaded and
 retained in memory for the duration of the application session.
 *     </li>
 *     <li>
 *         If a contextual family from a different properties file is needed, a separate instance of ConfigEnabler. This will cause all of the contextual
 families from that properties file to be loaded and added to any previously existing properties file contextual family mappings.
 *     </li>
 * </ul>
 * Due to the key behavior in each of the cases above, it is recommended that only <strong>absolute paths</strong> are specified, and <strong>not</strong> classpath
 resources!
 @author Charles Burrell (deltafront@gmail.com)
 @version 1.0
 */
public class ConfigEnabler
{
    private Map<String,String> mapping;
    private static Map<String,Map<String,String>> property_mappings;
    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigEnabler.class);
    static
    {
        if(null == property_mappings)
        {
            property_mappings = new HashMap<>();
        }
    }

    /**
     * Default constructor.
     * @param filename Name of properties file to be loaded. This should be an absolute path, and not simply a classpath resource.
     * @param family Contextual family.
     * @since 1.0
     */
    public ConfigEnabler(String filename, String family)
    {
        Validate.notNull(filename);
        Validate.notNull(family);
        if(!property_mappings.containsKey(filename))
        {
            Map<String,String>props = CustomPropertiesReader.read(filename);
            property_mappings.put(filename,props);
            LOGGER.trace(String.format("Hashing all properties for properties file '%s'.",filename));
        }
        Map<String,String>properties = property_mappings.get(filename);
        Validate.notEmpty(properties);
        Map<String,Map<String,String>>mappings = CustomMapper.getMappings(properties);
        Validate.notEmpty(mappings);
        mapping = mappings.get(family);
        Validate.notEmpty(mappings);
    }

    /**
     * Gets the property's value.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file, or default if not found.
     * @since 1.0
     */
    public Object getValue(String key, Object defaultValue)
    {
        Validate.notNull(defaultValue);
        LOGGER.trace(String.format("Getting value for key '%s' [default = '%s']",key,defaultValue));
        Object value;
        try
        {
            value = mapping.containsKey(key) ? mapping.get(key) : defaultValue;
        }
        catch (NullPointerException e)
        {
            value = defaultValue;
        }
        LOGGER.debug(String.format("Returning value '%s' for key '%s'.",String.valueOf(value),key));
        return value;
    }

    /**
     * Gets the property's value as a string.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a String, or default if not found.
     * @since 1.0
     */
    public String getString(String key, String defaultValue)
    {
        String value = String.valueOf(getValue(key,defaultValue));
        if(0 == value.length())
        {
            value = null;
        }
        return value;
    }
    /**
     * Gets the property's value as a Boolean.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a Boolean, or default if not found.
     * @since 1.0
     */
    public Boolean getBoolean(String key, Boolean defaultValue)
    {
        return Boolean.parseBoolean(String.valueOf(getValue(key,defaultValue)));
    }
    /**
     * Gets the property's value as a Long.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a Long, or default if not found.
     * @since 1.0
     */
    public Long getLong(String key, Long defaultValue)
    {
        return Long.parseLong(String.valueOf(getValue(key,defaultValue)));
    }
    /**
     * Gets the property's value as an Integer.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as an Integer, or default if not found.
     * @since 1.0
     */
    public Integer getInteger(String key, Integer defaultValue)
    {
        return Integer.parseInt(String.valueOf(getValue(key, defaultValue)));
    }
    /**
     * Gets the property's value as a Short.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a Short, or default if not found.
     * @since 1.0
     */
    public Short getShort(String key, Short defaultValue)
    {
        return Short.parseShort(String.valueOf(getValue(key, defaultValue)));
    }
    /**
     * Gets the property's value as a Double.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a Double, or default if not found.
     * @since 1.0
     */
    public Double getDouble(String key, Double defaultValue)
    {
        return Double.parseDouble(String.valueOf(getValue(key, defaultValue)));
    }
    /**
     * Gets the property's value as a Float.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a Float, or default if not found.
     * @since 1.0
     */
    public Float getFloat(String key, Float defaultValue)
    {
        return Float.parseFloat(String.valueOf(getValue(key, defaultValue)));
    }
    /**
     * Gets the property's value as a BigDecimal.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a BigDecimal, or default if not found.
     * @since 1.0
     */
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue)
    {
        return BigDecimal.valueOf(getDouble(key,defaultValue.doubleValue()));
    }
    /**
     * Gets the property's value as a BigInteger.
     * @param key Property value to get.
     * @param defaultValue Default value to return in case property is not found. 'Null' is not a valid default value.
     * @return Contextual family value from properties file as a BigInteger, or default if not found.
     * @since 1.0
     */
    public BigInteger getBigInteger(String key, BigInteger defaultValue)
    {
        return BigInteger.valueOf(getInteger(key, defaultValue.intValue()));
    }
    /**
     * Gets the property's value as an Integer.
     * @param key Property value to get.
     * @return Contextual family value from properties file as an Integer, or '0' if not found.
     * @since 1.0
     */
    public Integer getInteger(String key)
    {
        return getInteger(key,0);
    }
    /**
     * Gets the property's value as a String.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a String, or 'null' if not found.
     * @since 1.0
     */
    public String getString(String key)
    {
        return getString(key, "");
    }
    /**
     * Gets the property's value as a Boolean.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a String, or 'false' if not found.
     * @since 1.0
     */
    public Boolean getBoolean(String key)
    {
        return getBoolean(key,false);
    }
    /**
     * Gets the property's value as a Long.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a Long, or '0L' if not found.
     * @since 1.0
     */
    public Long getLong(String key)
    {
        return getLong(key,0L);
    }
    /**
     * Gets the property's value as a Short.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a Short, or '0' if not found.
     * @since 1.0
     */
    public Short getShort(String key)
    {
        return getShort(key, new Short("0"));
    }
    /**
     * Gets the property's value as a Double.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a Double, or '0.0D' if not found.
     * @since 1.0
     */
    public Double getDouble(String key)
    {
        return getDouble(key, 0.0D);
    }
    /**
     * Gets the property's value as a Long.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a Long, or '0' if not found.
     * @since 1.0
     */
    public Float getFloat(String key)
    {
        return getFloat(key, 0.0F);
    }
    /**
     * Gets the property's value as a BigDecimal.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a BigDecimal, or '0.0' if not found.
     * @since 1.0
     */
    public BigDecimal getBigDecimal(String key)
    {
        return getBigDecimal(key,new BigDecimal("0.0"));
    }
    /**
     * Gets the property's value as a BigInteger.
     * @param key Property value to get.
     * @return Contextual family value from properties file as a BigInteger, or '0' if not found.
     * @since 1.0
     */
    public BigInteger getBigInteger(String key)
    {
        return getBigInteger(key, BigInteger.ZERO);
    }
}
