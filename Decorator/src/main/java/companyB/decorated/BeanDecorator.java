package companyB.decorated;

import companyB.common.conversion.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class BeanDecorator
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BeanDecorator.class);
    private Converter converter;

    public BeanDecorator()
    {
        this.converter = new Converter();
    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param typeOf Type of class that is to be instantiated and decorated.
     * @param propertiesFileName Name of properties file to be used as a source for values.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated fields are of a type that is not supported.
     * @since 1.0
     */
    public <T> T decorate(Class<T> typeOf, String propertiesFileName) throws UnsupportedTypeException
    {
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
     * @since 1.0
     */
    public <T> T decorate(T instance, String propertiesFileName) throws UnsupportedTypeException
    {
        T out = null;
        Properties properties = new Properties();
        File file = new File(propertiesFileName);
        try
        {
            InputStream inputStream = new FileInputStream(file);
            if(propertiesFileName.contains(".xml"))
            {
                properties.loadFromXML(inputStream);
            }
            else
            {
                properties.load(inputStream);
            }
            out = decorate(instance,properties);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return out;
    }

    /**
     * Decorates the fields of a class that are annotated with the @Decorated annotation. See the documentation for supported types.
     * @param typeOf Type of class that is to be instantiated and decorated.
     * @param properties Properties object that contain key / value mappings.
     * @param <T> Generic type of class
     * @return Instance of class whose annotated fields have been decorated with the values found in the properties file.
     * @throws UnsupportedTypeException if any of the annotated fields are of a type that is not supported.
     * @since 1.0
     */
    public <T> T decorate(Class<T>typeOf, Properties properties) throws UnsupportedTypeException
    {
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
     * @since 1.0
     */
    public <T> T decorate(T instance, Properties properties) throws UnsupportedTypeException
    {
        T out = instance;
        try
        {
            Field[] fields = out.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                field.setAccessible(true);
                Decorated decorated = field.getAnnotation(Decorated.class);
                if(null != decorated)
                {
                    String name = getName(field,decorated);
                    LOGGER.debug(String.format("Resolved Name: %s",name));
                    String value = getValue(name,decorated,properties);
                    LOGGER.debug(String.format("Resolved Value: %s",value));
                    Object coerced = coerce(value,field.getType());
                    String classType = (null == coerced) ? "Null" : coerced.getClass().getCanonicalName();
                    LOGGER.debug(String.format("Coerced to %s [instance of %s]",String.valueOf(coerced),classType));
                    if(null != coerced && 0 != String.valueOf(coerced).length())
                    {
                        field.set(out,coerced);
                    }
                }
            }
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return out;
    }
    private String getName(Field field, Decorated decorated)
    {
        return (0 != decorated.alternateName().length()) ? decorated.alternateName() : field.getName();
    }
    private String getValue(String key, Decorated decorated, Properties properties)
    {
        String value = properties.getProperty(key);
        if(value == null || 0 == value.length())
        {
            value = decorated.defaultValue();
        }
        return value;
    }
    private Object coerce(String value, Class objectClassTypeClass) throws UnsupportedTypeException
    {
        Object out = value;
        if(!converter.isSupported(objectClassTypeClass))
        {
            throw new UnsupportedTypeException(objectClassTypeClass);
        }
        if(value != null && 0 < value.length())
        {
            if(converter.isNumberType(objectClassTypeClass))
            {
                out = converter.convertToNumber(value, objectClassTypeClass);
            }
            else if (converter.isBoolean(objectClassTypeClass))
            {
                out = converter.convertToBoolean(value);
            }
            else if (objectClassTypeClass.equals(char.class) || objectClassTypeClass.equals(Character.class))
            {
                out = value.charAt(0);
            }
            else if (converter.isBigType(objectClassTypeClass))
            {
                out = converter.convertToBig(value, objectClassTypeClass);
            }
            else if (converter.isByte(objectClassTypeClass))
            {
                out = converter.convertToByte(value);
            }
            else if(converter.isCharOrString(objectClassTypeClass))
            {
                out = converter.convertToStringOrChar(value, objectClassTypeClass);
            }
        }
        return out;
    }
}
