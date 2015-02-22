package companyB.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <strong>THE</strong> Object Instantiation Factory!
 *
 * @author cburrell deltafront@gmail.com
 * @version 1.0
 */
public abstract class FactoryUtils
{
    private static Logger log;
    private static Map<String, Object> objects;

    static
    {
        if (log == null)
        {
            log = LoggerFactory.getLogger(FactoryUtils.class);
        }
        if (objects == null)
        {
            objects = new HashMap<String, Object>();
        }
    }

    /**
     * Returns an object that is of the type specified by the class name, or null
     * if that class cannot be loaded.
     *
     * @param class_name Fully qualified class name.
     * @return object of type, or null
     * @since 1.0
     */
    public static Object loadObject(String class_name)
    {
        return loadObject(class_name, false);
    }

    /**
     * Returns an object that is of the type specified by the class name, or null
     * if that class cannot be loaded. If the static indicator is set to 'true', then this instance will either be
     * loaded from an internal map, or, if it isn't present, it will be loaded into the map for future retrieval
     *
     * @param class_name Fully qualified class name.
     * @param is_static  Whether or not to load class as a singleton.
     * @return object of type, or null
     * @since 1.0
     */
    public static Object loadObject(String class_name, boolean is_static)
    {
        Object out = null;
        try
        {
            if (is_static)
            {
                out = objects.get(class_name);
            }
            if (out == null)
            {
                out = Class.forName(class_name).newInstance();
                if (is_static)
                {
                    objects.put(class_name, out);
                }
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        return out;
    }
}