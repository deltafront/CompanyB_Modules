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
    /**
     * @param instance Instance to get all fields from.
     * @return All declared fields in instance.
     * @since 1.1.2
     */
    public Field[]getFields(Object instance)
    {
        Validate.notNull(instance);
        final Field[]fields = instance.getClass().getDeclaredFields();
        LOGGER.trace("Returning {} fields from instance of {}.",
                fields.length,instance.getClass().getCanonicalName());
        return fields;
    }

    /**
     * @param fieldName Name of field to get.
     * @param instance Instance to get field from.
     * @return Value from instance.field.
     * @since 1.2.1
     */
    public Field getField(String fieldName, Object instance)
    {
        Field field = null;
        try
        {
            field = instance.getClass().getDeclaredField(fieldName);
            LOGGER.trace("Returning field {}.{}.",instance.getClass().getCanonicalName(),fieldName);
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
     * @since 1.1.2
     */
    public <T extends Annotation> T getAnnotation(Class<T>annotationClass, Field field)
    {
        field.setAccessible(true);
        final T out = field.getAnnotation(annotationClass);
        LOGGER.trace("Annotation '{}' found on field '{}'? {}",
                annotationClass.getCanonicalName(),field.getName(),null != out);
        return out;
    }

    /**
     * @param fieldName Name of field to set value to.
     * @param instance Instance to get field from.
     * @param value Value to set field to.
     * @since 1.1.2
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
     * @since 1.1.2
     */
    public void setField(Field field, Object instance, Object value)
    {
        try
        {
            field.setAccessible(true);
            field.set(instance, value);
            LOGGER.trace("{}.{} set to {}.",
                    instance.getClass().getCanonicalName(),field.getName(),String.valueOf(value));
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
     * @since 1.2.1
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
     * @since 1.2.1
     */
    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(Field field, Object instance)
    {
        T out = null;
        try
        {
            field.setAccessible(true);
            out = (T)field.get(instance);
            LOGGER.trace("{}.{} returned value of '{}'.",
                    instance.getClass().getCanonicalName(),field.getName(),String.valueOf(out));
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
}
