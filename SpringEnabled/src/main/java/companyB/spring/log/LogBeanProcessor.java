package companyB.spring.log;

import companyB.common.utils.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Spring based processor for Auto-injecting SLF4J Log fields.
 * @author Charles Burrell (deltafront@gmail.com)
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @since 1..0
 */
public class LogBeanProcessor implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException
    {

        final FieldUtils fieldUtils = new FieldUtils();
        Field[]fields = fieldUtils.getFields(o);
        for(Field field : fields)
        {
            if(Logger.class.equals(field.getType()))
            {
                Log log = fieldUtils.getAnnotation(Log.class,field);
                if(null != log)
                {
                    String name = getName(log,o);
                    Logger logger = LoggerFactory.getLogger(name);
                    fieldUtils.setField(field,o,logger);
                }
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
