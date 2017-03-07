package companyB.encrypted;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Performs the decoration of Encrypted Fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class EncryptedDecorator
{
    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptedDecorator.class);

    /**
     * Decorates a class. For each String field annotated with the @Encrypted annotation a default value must be present.
     * Additionally, the class must have a zero-args constructor.
     * @param c Class to be decorated.
     * @param <T> Type parameter.
     * @return Decorated instance of class.
     */
    public static <T> T decorate(Class<T> c)
    {
        T out = null;
        try
        {
            out = decorate(c.newInstance());
        }
        catch (InstantiationException | IllegalAccessException  e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
    /**
     * Decorates a class. For each String field annotated with the @Encrypted annotation a value must be present.
     * @param instance Instance to be decorated.
     * @param <T> Type parameter.
     * @return Decorated instance.
     */
    public static <T> T decorate(T instance)
    {
        try
        {
            final Field[]fields = instance.getClass().getDeclaredFields();
            for(final Field field : fields) decorateCandidateField(instance, field);
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return instance;
    }

    private static <T> void decorateCandidateField(T instance, Field field) throws IllegalAccessException
    {
        if(String.class.equals(field.getType())) setAndDecorateField(instance, field);
    }

    private static <T> void setAndDecorateField(T instance, Field field) throws IllegalAccessException
    {
        field.setAccessible(true);
        final Encrypted encrypted = field.getAnnotation(Encrypted.class);
        if(null != encrypted)decorateField(instance, field, encrypted);
    }

    private static <T> void decorateField(T instance, Field field, Encrypted encrypted) throws IllegalAccessException
    {
        final Object fromClass = field.get(instance);
        final Function<String,String>function = encrypted.algorithm().encrypt;
        Validate.notNull(fromClass);
        final String plainText = (fromClass instanceof  EncryptedWrapper) ?
                ((EncryptedWrapper)fromClass).getValue() : String.valueOf(fromClass);
        final String encryptedValue = function.apply(plainText);
        field.set(instance,encryptedValue);
    }
}
