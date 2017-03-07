package companyB.configuration;

import companyB.common.utils.FieldUtils;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * This class decorates an instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation.
 * @see companyB.configuration.ConfigEnabled
 * @author Charles Burrell (deltafront@gmail.com)
 * @version  1.0.0
 */
public class ClassLevelConfigEnabler
{
    private final FieldUtils fieldUtils = new FieldUtils();
    /**
     * Decorates instance of a class that has ConfigEnabler fields annotated with the ConfigEnabled annotation. All annotated
     * instances will be injected with the correct ConfigEnabler contexts.
     * @param instance instance to be decorated.
     */
    public void decorate(Object instance)
    {
        Validate.notNull(instance,"Instance not provided.");
        final Field[]fields = fieldUtils.getFields(instance);
        Arrays.asList(fields).forEach((field)-> processFields(instance, field));
    }

    private void processFields(Object instance,Field field)
    {
        final ConfigEnabled configEnabled = fieldUtils.getAnnotation(ConfigEnabled.class,field);
        if(ConfigEnabler.class.equals(field.getType()) && null != configEnabled)
            setConfigEnabledField(instance,field, configEnabled);
    }

    private void setConfigEnabledField(Object instance, Field field, ConfigEnabled configEnabled)
    {
        final String filename = configEnabled.filename();
        final String family = configEnabled.family();
        final ConfigEnabler configEnabler = new ConfigEnabler(filename,family);
        fieldUtils.setField(field,instance,configEnabler);
    }
}