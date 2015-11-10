package companyB.spring.configurationEnabled;

import companyB.configuration.ClassLevelConfigEnabler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring based processor for Auto-injecting Configuration fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @since 1..0
 */
public class ConfigurationEnabledBeanProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {
        final ClassLevelConfigEnabler classLevelConfigEnabler = new ClassLevelConfigEnabler();
        classLevelConfigEnabler.decorate(o);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException
    {
        return o;
    }
}
