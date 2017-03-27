package companyB.common.utils;

import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Field utilities for low-level access to declared fields.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class FieldUtils extends UtilityBase
{
    /**
     * @param instance Instance to get all fields from.
     * @return All declared fields in instance.
     */
    public Field[]getFields(Object instance)
    {
        Validate.notNull(instance);
        return instance.getClass().getDeclaredFields();
    }

    /**
     * @param fieldName Name of field to get.
     * @param instance Instance to get field from.
     * @return Value from instance.field.
     */
    public Field getField(String fieldName, Object instance)
    {
        Field field = null;
        try
        {
            field = instance.getClass().getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        return field;

    }

    /**
     * @param annotationClass Class extending Annotation.
     * @param field Field to get annotation from.
     * @param <T> Generic type declaration.
     * @return Annotation from field. This can be null.
     */
    public <T extends Annotation> T getAnnotation(Class<T>annotationClass, Field field)
    {
        field.setAccessible(true);
        return field.getAnnotation(annotationClass);
    }

    /**
     * @param fieldName Name of field to set value to.
     * @param instance Instance to get field from.
     * @param value Value to set field to.
     */
    public void setField(String fieldName, Object instance, Object value)
    {
        Validate.notNull(instance,"Valid instance must be provided.");
        Validate.notBlank(fieldName,"Field name must be provided.");
        final Field field = getField(fieldName,instance);
        Validate.notNull(field,String.format("Field '%s.%s' does not exist.",
                instance.getClass().getCanonicalName(),fieldName));
        setField(field,instance,value);
    }

    /**
     * @param field Field to set value to.
     * @param instance Instance to get field from.
     * @param value Value to set field to.
     */
    public void setField(Field field, Object instance, Object value)
    {
        try
        {
            field.setAccessible(true);
            field.set(instance, value);
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
    }

    /**
     * @param fieldName Name of field to get value from..
     * @param instance Instance to get field value from.
     * @return Value from instance.field.
     */
    public <T>T getFieldValue(String fieldName, Object instance)
    {
        Validate.notNull(instance,"Valid instance must be provided.");
        Validate.notBlank(fieldName,"Field name must be provided.");
        final Field field = getField(fieldName,instance);
        Validate.notNull(field,String.format("Field %s.%s does not exist.",
                instance.getClass().getCanonicalName(),fieldName));
        return getFieldValue(field,instance);
    }

    /**
     * @param field Field to get value from.
     * @param instance Instance to get field value from.
     * @return Value from instance.field.
     */
    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(Field field, Object instance)
    {
        T out = null;
        try
        {
            field.setAccessible(true);
            out = (T)field.get(instance);
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
}
