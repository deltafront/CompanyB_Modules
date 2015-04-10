package companyB.spring.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class BeanProcessingUtils
{
    public Field[]getFields(Object instance)
    {
        return instance.getClass().getDeclaredFields();
    }
    public <T extends Annotation> T getAnnotation(Class<T>annotationClass, Field field)
    {
        return field.getAnnotation(annotationClass);
    }
    public void setField(Field field, Object instance, Object value)
    {
        try
        {
            field.setAccessible(true);
            field.set(instance, value);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
