package companyB.spring.eventLogger;

import companyB.eventlogger.EventLoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring based processor for Auto-injecting EventLogger fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 *
 */
public class EventLoggerBeanProcessor implements BeanPostProcessor
{

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {
        final EventLoggerFactory eventLoggerFactory = new EventLoggerFactory();
        eventLoggerFactory.decorate(o);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException
    {
        return o;
    }
}
