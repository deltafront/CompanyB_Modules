package companyB.spring.enabled.test;

import companyB.spring.eventLogger.EventLoggerBeanProcessor;
import org.testng.annotations.Test;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
@Test(groups = {"unit","spring","event.logger.bean.processor"})
public class EventLoggerTest
{


    public void happyPath()
    {

    }
    private void process(boolean doBefore)
    {
        EventLoggerBeanProcessor processor = new EventLoggerBeanProcessor();
        //TODO - write rest of class.
    }
    public static class EventLoggerEnabledTestClass
    {

    }
}
