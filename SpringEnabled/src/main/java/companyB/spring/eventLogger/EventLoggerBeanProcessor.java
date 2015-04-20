package companyB.spring.eventLogger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring based processor for Auto-injecting EventLogger fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @since 1..0
 */
public class EventLoggerBeanProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {
        //TODO - Plug in the EventLogger decorator here
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException
    {
        return o;
    }
}
