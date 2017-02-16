package companyB.configuration;

import companyB.common.utils.FieldUtils;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;

/**
 * This class decorates an instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation.
 * @see companyB.configuration.ConfigEnabled
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 */
public class ClassLevelConfigEnabler
{
    /**
     * Decorates instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation. All annotated
     * instances will be injected with the correct ConfigEnabler contexts.
     * @param instance instance to be decorated.
     * @since 1.0.0
     */
    public void decorate(Object instance)
    {
       final FieldUtils fieldUtils  = new FieldUtils();
        Validate.notNull(instance,"Instance not provided.");
        final Field[]fields = fieldUtils.getFields(instance);
        for(final Field field : fields)
            processFields(instance, fieldUtils, field);
    }

    private void processFields(Object instance, FieldUtils fieldUtils, Field field)
    {
        final ConfigEnabled configEnabled = fieldUtils.getAnnotation(ConfigEnabled.class,field);
        if(ConfigEnabler.class.equals(field.getType()) && null != configEnabled)
            setConfigEnabledField(instance, fieldUtils, field, configEnabled);
    }

    private void setConfigEnabledField(Object instance, FieldUtils fieldUtils, Field field, ConfigEnabled configEnabled)
    {
        final String filename = configEnabled.filename();
        final String family = configEnabled.family();
        final ConfigEnabler configEnabler = new ConfigEnabler(filename,family);
        fieldUtils.setField(field,instance,configEnabler);
    }
}