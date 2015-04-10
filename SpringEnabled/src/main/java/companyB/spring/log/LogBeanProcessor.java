package companyB.spring.log;

import companyB.spring.utils.BeanProcessingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class LogBeanProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {

        final BeanProcessingUtils beanProcessingUtils = new BeanProcessingUtils();
        Field[]fields = beanProcessingUtils.getFields(o);
        for(Field field : fields)
        {
            Log log = beanProcessingUtils.getAnnotation(Log.class,field);
            if(field.getType().equals(Logger.class) && null != log)
            {
                String name = getName(log,o);
                Logger logger = LoggerFactory.getLogger(name);
                beanProcessingUtils.setField(field,o,logger);
            }
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException
    {
        return o;
    }
    private String getName(Log log, Object instance)
    {
       return "".equals(log.name()) ?
               instance.getClass().getCanonicalName() : log.name();
    }
}
