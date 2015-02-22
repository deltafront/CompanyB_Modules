package companyB.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * This class provides a simple wrapper for java.util.Properties.
 * It allows a property to be retrieved using a single-line of code.
 * This class is designed to work with a key=value type properties file.
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public abstract class PropertiesUtils
{
    private static Properties props;
    private static Map<String, Properties> props_map;

    static
    {
        if (props_map == null)
        {
            props_map = Collections.synchronizedMap(new HashMap<String, Properties>());
        }
    }

    /**
     * Returns the property value associated with 'key'.
     *
     * @param path location of properties file
     * @param key attribute to get property value for
     * @return value associated with key, or null
     * @since 1.0
     */
    public static String getProperty(String path, String key)
    {
        String out = null;
        loadProps(path);
        out = (isEmpty(key)) ? null : props.getProperty(key);
        return out;
    }

    /**
     * Returns a map of all properties contained within field located at 'path'
     * @param path path at which desired file is located
     * @return HashMap of all properties contained within field located at 'path'
     * @since 1.0
     */
    @SuppressWarnings({"unchecked", "WhileLoopReplaceableByForEach"})
    public static HashMap<String, String> getProperties(String path)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        if (!isEmpty(path) && !map.containsKey(path))
        {
            loadProps(path);
        }
        if (!isEmpty(path))
        {
            Properties _props = props_map.get(path);
            java.util.Set keys_set = _props.keySet();
            java.util.Iterator keys = keys_set.iterator();
            while (keys.hasNext())
            {
                String key = (String) keys.next();
                String value = (String) _props.get(key);
                map.put(key, value);
            }
        }
        else
        {
            map = null;
        }
        return map;
    }

    //loads the properties from a properties file
    private static void loadProps(String path)
    {
        props = new Properties();
        try
        {
            if (isEmpty(path))
            {
                throw new IOException("Filename must be provided");
            }
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            props.load(fis);
            props_map.put(path, props);
        }
        catch (IOException e)
        {
            throw new IllegalStateException(
                    "ClasspathPropertiesLoader: Unable to find the file on the classpath:\n",
                    e);
        }
    }
}
