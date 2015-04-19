package companyB.common.utils;

import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Field utilities for low-level access to declared fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.1.2
 */
public class FieldUtils extends UtilityBase
{
    //TODO - document and test me!
    public Field[]getFields(Object instance)
    {
        Validate.notNull(instance);
        Field[]fields = instance.getClass().getDeclaredFields();
        LOGGER.trace(String.format("Returning %d fields from instance of %s.",
                fields.length,instance.getClass().getCanonicalName()));
        return fields;
    }
    public <T extends Annotation> T getAnnotation(Class<T>annotationClass, Field field)
    {
        T out = field.getAnnotation(annotationClass);
        LOGGER.trace(String.format("Annotation '%s' found on field '%s'? %b",
                annotationClass.getCanonicalName(),field.getName(),null != out));
        return out;
    }
    public void setField(Field field, Object instance, Object value)
    {
        try
        {
            field.setAccessible(true);
            field.set(instance, value);
            LOGGER.trace(String.format("%s.%s set to %s.",
                    instance.getClass().getCanonicalName(),field.getName(),String.valueOf(value)));
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
    }
    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(Field field, Object instance)
    {
        T out = null;
        try
        {
            field.setAccessible(true);
            out = (T)field.get(instance);
            LOGGER.trace(String.format("%s.%s returned value of '%s'.",
                    instance.getClass().getCanonicalName(),field.getName(),String.valueOf(out)));
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
}
