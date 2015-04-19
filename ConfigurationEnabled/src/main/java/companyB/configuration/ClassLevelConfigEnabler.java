package companyB.configuration;

import companyB.common.utils.FieldUtils;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;

/**
 * This class decorates an instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation.
 * @see companyB.configuration.ConfigEnabled
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class ClassLevelConfigEnabler
{
    private static final FieldUtils fieldUtils  = new FieldUtils();

    /**
     * Decorates instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation. All annotated
     * instances will be injected with the correct ConfigEnabler contexts.
     * @param instance instance to be decorated.
     * @since 1.0
     */
    public void decorate(Object instance)
    {
        Validate.notNull(instance,"Instance not provided.");
        Field[]fields = fieldUtils.getFields(instance);
        for(Field field : fields)
        {
            ConfigEnabled configEnabled = fieldUtils.getAnnotation(ConfigEnabled.class,field);
            if(ConfigEnabler.class.equals(field.getType()) && null != configEnabled)
            {
                String filename = configEnabled.filename();
                String family = configEnabled.family();
                ConfigEnabler configEnabler = new ConfigEnabler(filename,family);
                fieldUtils.setField(field,instance,configEnabler);
            }
        }
    }
}