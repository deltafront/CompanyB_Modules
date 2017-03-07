package companyB.decorated;

import companyB.common.conversion.Converter;
import companyB.common.utils.FieldUtils;
import companyB.common.utils.PropertiesUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
public class BeanDecorator
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BeanDecorator.class);
    private final Converter converter;
    private final FieldUtils fieldUtils;

    public BeanDecorator()
    {
        this.converter = new Converter();
        this.fieldUtils = new FieldUtils();
    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param typeOf Type of class that is to be instantiated and decorated.
     * @param propertiesFileName Name of properties file to be used as a source for values.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated fields are of a type that is not supported.
     */
    public <T> T decorate(Class<T> typeOf, String propertiesFileName) throws UnsupportedTypeException
    {
        Validate.notNull(typeOf,"Type of class to be decorated is required.");
        Validate.notBlank(propertiesFileName,"Properties File Name is required.");
        T out = null;
        try
        {
            out = typeOf.newInstance();
            out = decorate(out,propertiesFileName);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param instance Instance of class that is to be instantiated and decorated.
     * @param propertiesFileName Name of properties file to be used as a source for values.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated field are of a type that is not supported.
     */
    public <T> T decorate(T instance, String propertiesFileName) throws UnsupportedTypeException
    {
        Validate.notNull(instance,"Instance of class to be decorated is required.");
        Validate.notBlank(propertiesFileName,"Properties File Name is required.");
        final PropertiesUtils propertiesUtils = new PropertiesUtils();
        final Properties properties = new Properties();
        final Map<String,String>map= propertiesUtils.getProperties(propertiesFileName);
        map.keySet().forEach(_key ->
        {
            final String key = String.valueOf(_key);
            properties.setProperty(String.valueOf(key),map.get(key)) ;
        });
        return decorate(instance,properties);
    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param typeOf Type of class that is to be instantiated and decorated.
     * @param properties Properties object that contain key / value mappings.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated fields are of a type that is not supported.
     */
    public <T> T decorate(Class<T>typeOf, Properties properties) throws UnsupportedTypeException
    {
        Validate.notNull(typeOf,"Type of class to be decorated is required.");
        Validate.notNull(properties,"Properties are required.");
        T out = null;
        try
        {
            out = typeOf.newInstance();
            out = decorate(out,properties);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;

    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param instance Instance of class that is to be instantiated and decorated.
     * @param properties Properties object that contain key / value mappings.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated fields are of a type that is not supported.
     */
    public <T> T decorate(T instance, Properties properties) throws UnsupportedTypeException
    {
        Validate.notNull(instance,"Instance of class to be decorated is required.");
        Validate.notNull(properties,"Properties are required.");
        final Field[] fields = fieldUtils.getFields(instance);
        for (final Field field : fields)decorateField(instance, properties, field);
        return instance;
    }

    private <T> void decorateField(T instance, Properties properties, Field field) throws UnsupportedTypeException
    {
        field.setAccessible(true);
        Decorated decorated = field.getAnnotation(Decorated.class);
        if(null != decorated) decorateField(instance, properties, field, decorated);
    }

    private <T> void decorateField(T instance, Properties properties, Field field, Decorated decorated) throws UnsupportedTypeException
    {
        final String name = getName(field,decorated);
        final String value = getValue(name,decorated,properties);
        final Object coerced = coerce(value,field.getType());
        final String classType = (null == coerced) ? "Null" : coerced.getClass().getCanonicalName();
        if(null != coerced && 0 != String.valueOf(coerced).length()) fieldUtils.setField(field, instance, coerced);
    }

    private String getName(Field field, Decorated decorated)
    {
        return (0 != decorated.alternateName().length()) ? decorated.alternateName() : field.getName();
    }
    private String getValue(String key, Decorated decorated, Properties properties)
    {
        String value = properties.getProperty(key);
        if(value == null || 0 == value.length()) value = decorated.defaultValue();
        return value;
    }
    private Object coerce(String value, Class objectClassTypeClass) throws UnsupportedTypeException
    {
        Object out = value;
        if(!converter.isSupported(objectClassTypeClass)) throw new UnsupportedTypeException(objectClassTypeClass);
        if(value != null && 0 < value.length()) out = getConvertedObject(value, objectClassTypeClass, out);
        return out;
    }

    private Object getConvertedObject(String value, Class objectClassTypeClass, Object out)
    {
        Object cleaned = out;
        if(converter.isNumberType(objectClassTypeClass))
            cleaned = converter.convertToNumber(value, objectClassTypeClass);
        if (converter.isBoolean(objectClassTypeClass))
            cleaned = converter.convertToBoolean(value);
        if (converter.isBigType(objectClassTypeClass))
            cleaned = converter.convertToBig(value, objectClassTypeClass);
        if (converter.isByte(objectClassTypeClass))
            cleaned = converter.convertToByte(value);
        if(converter.isCharOrString(objectClassTypeClass))
            cleaned = converter.convertToStringOrChar(value, objectClassTypeClass);
        return cleaned;
    }
}
