package companyB.eventlogger.test;

import companyB.eventlogger.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

@Test(groups = {"unit","event.logger","event.logger.test"})
public class EventLoggerTest
{
    private EventLoggerFactory eventLoggerFactory;
    private EventLogger eventLogger;
    private EventCode eventCode;
    private String logMessage;
    private Properties properties;
    private LogMessageFormatter logMessageFormatter;
    Throwable throwable;

    @BeforeMethod
    public void before()
    {
        eventLoggerFactory = new EventLoggerFactory();
        logMessageFormatter = new DefaultLogMessageFormatter();
        eventLogger = eventLoggerFactory.getEventLogger(this).withLogMessageFormatter(logMessageFormatter);
        eventCode = new EventCode("TestEventCode",42);
        properties = new Properties();
        properties.put("this","that");
        logMessage = "this is a test message";
        throwable = new Error("This is an error.");
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void withNullLogMessageFormatter()
    {
        eventLoggerFactory.getEventLogger(this).withLogMessageFormatter(null);
        fail("NullPointerException should have been thrown!");
    }
    @Test
    public void withAdHocLogMessageFormatter()
    {
        LogMessageFormatter logMessageFormatter = new LogMessageFormatter()
        {
            @Override
            public String formatLogMessage(EventCode eventCode, String message, Properties attributes)
            {
                return String.format("[%s=>%s] %s",eventCode.getName(),eventCode.getCode(),message);
            }
        };
        EventLogger eventLogger = eventLoggerFactory.getEventLogger(this).withLogMessageFormatter(logMessageFormatter);
        assertEquals(logMessageFormatter.getClass(), eventLogger.getLogMessageFormatter().getClass());
    }
    @Test
    public void defaultLogFormatter()
    {
        EventLogger eventLogger = eventLoggerFactory.getEventLogger(this);
        assertEquals(DefaultLogMessageFormatter.class, eventLogger.getLogMessageFormatter().getClass());
    }

    @Test
    public void testTrace()
    {
       runTest(EventLogger.State.TRACE);
    }
    @Test
    public void testDebug()
    {
        runTest(EventLogger.State.DEBUG);
    }
    @Test
    public void testInfo()
    {
        runTest(EventLogger.State.INFO);
    }
    @Test
    public void testWarn()
    {
        runTest(EventLogger.State.WARN);
    }
    @Test
    public void testError()
    {
        runTest(EventLogger.State.ERROR);
    }
    private void runTest(EventLogger.State state)
    {
        final String expectedMessage = logMessageFormatter.formatLogMessage(eventCode, logMessage, properties);
        switch (state)
        {
            case TRACE:
                eventLogger.trace(eventCode, logMessage,null,properties);
                eventLogger.trace(eventCode, logMessage,throwable,properties);
                break;
            case WARN:
                eventLogger.warn(eventCode, logMessage, null, properties);
                eventLogger.warn(eventCode, logMessage, throwable, properties);
                break;
            case DEBUG:
                eventLogger.debug(eventCode, logMessage, null, properties);
                eventLogger.debug(eventCode, logMessage, throwable, properties);
                break;
            case INFO:
                eventLogger.info(eventCode, logMessage, null, properties);
                eventLogger.info(eventCode, logMessage, throwable, properties);
                break;
            case ERROR:
                eventLogger.error(eventCode, logMessage, null, properties);
                eventLogger.error(eventCode, logMessage, throwable, properties);
                break;
        }
        assertEquals(state,eventLogger.getState());
        assertEquals(expectedMessage,eventLogger.getLastMessage());
    }
}