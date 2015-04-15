package companyB.configuration;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * This class decorates an instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation.
 * @see companyB.configuration.ConfigEnabled
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class ClassLevelConfigEnabler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLevelConfigEnabler.class);

    /**
     * Decorates instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation. All annotated
     * instances will be injected with the correct ConfigEnabler contexts.
     * @param instance instance to be decorated.
     * @since 1.0
     */
    public void decorate(Object instance)
    {
        Validate.notNull(instance,"Instance not provided.");
        Field[]fields = instance.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            ConfigEnabled configEnabled = field.getAnnotation(ConfigEnabled.class);
            if(ConfigEnabler.class.equals(field.getType()) && null != configEnabled)
            {
                try
                {
                    field.setAccessible(true);
                    String filename = configEnabled.filename();
                    String family = configEnabled.family();
                    ConfigEnabler configEnabler = new ConfigEnabler(filename,family);
                    field.set(instance,configEnabler);


                }
                catch (IllegalAccessException e)
                {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
    }
}