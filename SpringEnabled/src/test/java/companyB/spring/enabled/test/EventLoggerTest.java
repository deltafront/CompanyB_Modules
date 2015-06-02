package companyB.spring.enabled.test;

import companyB.eventlogger.EventLog;
import companyB.eventlogger.EventLogger;
import companyB.eventlogger.LogMessageFormatter;
import companyB.spring.eventLogger.EventLoggerBeanProcessor;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
@Test(groups = {"unit","spring","event.logger.bean.processor"})
public class EventLoggerTest
{
    public void testBefore()
    {
        process(true);
    }

    public void testAfter()
    {
        process(false);
    }
    private void process(boolean doBefore)
    {
        EventLoggerBeanProcessor processor = new EventLoggerBeanProcessor();
        EventLoggerEnabledTestClass eventLoggerEnabledTestClass = new EventLoggerEnabledTestClass();
        eventLoggerEnabledTestClass = (doBefore) ?
                (EventLoggerEnabledTestClass)processor.postProcessBeforeInitialization(eventLoggerEnabledTestClass, "Test") :
                (EventLoggerEnabledTestClass)processor.postProcessAfterInitialization(eventLoggerEnabledTestClass,"Test");
        assertNotNull(eventLoggerEnabledTestClass);
        if(doBefore)
        {
            EventLogger eventLogger = eventLoggerEnabledTestClass.eventLogger;
            assertNotNull(eventLogger);
            assertEquals("Foo",eventLogger.getName());
            LogMessageFormatter fromDecorated = eventLoggerEnabledTestClass.eventLogger.getLogMessageFormatter();
            assertNotNull(fromDecorated);
            assertTrue(fromDecorated instanceof AdHocLogMessageFormatter);
        }
        else
        {
            assertNull(eventLoggerEnabledTestClass.eventLogger);
        }
        assertNull(eventLoggerEnabledTestClass.nonEventLogger);
    }
}
class EventLoggerEnabledTestClass
{
    @EventLog(name = "Foo", logMessageFormatter = AdHocLogMessageFormatter.class)
    EventLogger eventLogger;
    String test;
    EventLogger nonEventLogger;
}
