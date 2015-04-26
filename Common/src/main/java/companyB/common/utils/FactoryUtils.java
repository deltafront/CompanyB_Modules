package companyB.common.utils;import org.apache.commons.lang3.Validate;import java.lang.reflect.Constructor;import java.lang.reflect.InvocationTargetException;import java.util.HashMap;import java.util.Map;/** * <strong>THE</strong> Object Instantiation Factory! * * @author cburrell deltafront@gmail.com * @since  1.0.0 */public class FactoryUtils extends UtilityBase{    private static Map<String, Object> objects = new HashMap<>();    /**     * Returns an object that is of the type specified by the class name, or null     * if that class cannot be loaded.     *     * @param class_name Fully qualified class name.     * @return object of type, or null     * @since 1.0.0     */    public Object loadObject(String class_name)    {        return loadObject(class_name, false);    }    /**     * Returns an object that is of the type specified by the class name, or null     * if that class cannot be loaded. If the static indicator is set to 'true', then this instance will either be     * loaded from an internal map, or, if it isn't present, it will be loaded into the map for future retrieval     *     * @param class_name Fully qualified class name.     * @param is_static  Whether or not to load class as a singleton.     * @return object of type, or null     * @since 1.0.0     */    public Object loadObject(String class_name, boolean is_static)    {        Validate.notBlank(class_name,"Name of class is required.");        Object out = null;        try        {            if (is_static) out = objects.get(class_name);            if (out == null)            {                out = Class.forName(class_name).newInstance();                if (is_static) objects.put(class_name, out);            }        }        catch (Exception e)        {            LOGGER.error(e.getMessage(), e);        }        return out;    }    /**     * Gets an instance of the specified class using the supplied constructor arguments.     * @param c Class that needs to be instantiated.     * @param args Array of constructor arguments.     * @param <T> Type parameter.     * @return Instance of the specified class using the supplied constructor arguments.     * @since 1.0.0     */    @SuppressWarnings({"unchecked", "ConstantConditions"})    public <T> T getInstance(Class<T>c, Object[]args)    {        T out = null;        Constructor[] constructors = c.getConstructors();        for(Constructor constructor : constructors)        {            Class[]params = constructor.getParameterTypes();            if(params.length == args.length)            {                boolean instantiated;                try                {                    out = (T)constructor.newInstance(args);                    instantiated = true;                }                catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)                {                    LOGGER.warn(e.getMessage());                    LOGGER.trace("Exception thrown when trying to instantiate a new instance. Continuing to next eligible constructor.");                    continue;                }                if(instantiated)                {                    break;                }            }        }        return out;    }}