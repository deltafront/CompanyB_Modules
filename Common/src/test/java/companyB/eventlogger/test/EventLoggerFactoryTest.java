package companyB.eventlogger.test;

import companyB.eventlogger.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static junit.framework.TestCase.fail;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","event.logger","event.logger.factory"})
public class EventLoggerFactoryTest extends TestBase
{
    private EventLogger eventLogger;
    private EventLoggerFactory eventLoggerFactory;
    public static class AdHocLogMessageFormatter implements LogMessageFormatter
    {
        @Override
        public String formatLogMessage(EventCode eventCode, String message, Properties attributes)
        {
            return null;
        }
    }
    public static class TestClassDefault
    {
        @EventLog()
        EventLogger eventLogger;
    }
    public static class TestClassNamed
    {
        @EventLog(name = "Foo")
        EventLogger eventLogger;
    }
    public static class TestClassFinal
    {
        @EventLog
        final static EventLogger eventLogger = null;
    }
    public static class TestClassSealed
    {
        private TestClassSealed(){}
        @EventLog
        EventLogger eventLogger;
    }
    public static class TestClassAdHocFormatter
    {
        @EventLog(name = "AdHoc",logMessageFormatter =AdHocLogMessageFormatter.class)
        EventLogger eventLogger;
    }

    @BeforeMethod
    public void before()
    {
        eventLoggerFactory = new EventLoggerFactory();
    }


    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullString()
    {
        String nullString = null;
        eventLogger = eventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullClass()
    {
        Class nullString = null;
        eventLogger = eventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullObject()
    {
        Object nullString = null;
        eventLogger = eventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    public void testWithValidStringName()
    {
        String name = "foo.com";
        eventLogger = eventLoggerFactory.getEventLogger(name);
        validateNotNull(eventLogger);
        validateEquality(EventLogger.State.INIT, eventLogger.getState());
        validateEquality(name,eventLogger.getName());
    }
    public void testWithValidStringNameTwoInstances()
    {
        String name = "foo.com";
        EventLogger eventLogger_1 = eventLoggerFactory.getEventLogger(name);
        EventLogger eventLogger_2 = eventLoggerFactory.getEventLogger(name);
        validateNotNull(eventLogger_1);
        validateNotNull(eventLogger_2);
        validateEquality(EventLogger.State.INIT, eventLogger_1.getState());
        validateEquality(EventLogger.State.INIT, eventLogger_2.getState());
        validateEquality(name,eventLogger_1.getName());
        validateEquality(name,eventLogger_2.getName());
        validateEquality(eventLogger_1.hashCode(), eventLogger_2.hashCode());
    }
    public void testWithValidClass()
    {
        Class c = this.getClass();
        eventLogger = eventLoggerFactory.getEventLogger(c);
        validateNotNull(eventLogger);
        validateEquality(EventLogger.State.INIT, eventLogger.getState());
        validateEquality(c.getCanonicalName(),eventLogger.getName());
    }
    public void testWithValidObjectInstance()
    {
        Integer instance = -1;
        String hashCode = String.valueOf(instance.hashCode());
        eventLogger = eventLoggerFactory.getEventLogger(instance);
        validateNotNull(eventLogger);
        validateEquality(EventLogger.State.INIT, eventLogger.getState());
        validateEquality(hashCode,eventLogger.getName());
    }
    public void testSingletonInstances()
    {
        EventLogger eventLogger1 = eventLoggerFactory.getEventLogger(this.getClass());
        EventLogger eventLogger2 = eventLoggerFactory.getEventLogger(this.getClass());
        validateNotNull(eventLogger1);
        validateNotNull(eventLogger2);
        validateEquality(eventLogger1.hashCode(), eventLogger2.hashCode());

    }
    public void testTestClassDefaultNotInstantiated()
    {
        TestClassDefault testClassDefault = eventLoggerFactory.decorate(TestClassDefault.class);
        validateNotNull(testClassDefault);
        EventLogger eventLogger = testClassDefault.eventLogger;
        validateNotNull(eventLogger);
        validateEquality(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
        LogMessageFormatter logMessageFormatter = eventLogger.getLogMessageFormatter();
        validateNotNull(logMessageFormatter);
        validateTrue(logMessageFormatter instanceof DefaultLogMessageFormatter);
    }
    public void testTestClassDefaultInstantiated()
    {
        TestClassDefault testClassDefault_1 = new TestClassDefault();
        TestClassDefault testClassDefault_2 = eventLoggerFactory.decorate(testClassDefault_1);
        validateNotNull(testClassDefault_2);
        EventLogger eventLogger = testClassDefault_2.eventLogger;
        validateNotNull(eventLogger);
        validateEquality(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
        validateEquality(testClassDefault_1.hashCode(), testClassDefault_2.hashCode());
    }
    public void testTestClassNamedNotInstantiated()
    {
        TestClassNamed testClassNamed = eventLoggerFactory.decorate(TestClassNamed.class);
        validateNotNull(testClassNamed);
        EventLogger eventLogger = testClassNamed.eventLogger;
        validateNotNull(eventLogger);
        validateEquality("Foo", eventLogger.getName());
    }
    public void testTestClassNamedInstantiated()
    {
        TestClassNamed testClassNamed_1 = new TestClassNamed();
        TestClassNamed testClassNamed_2 = eventLoggerFactory.decorate(testClassNamed_1);
        validateNotNull(testClassNamed_2);
        EventLogger eventLogger = testClassNamed_2.eventLogger;
        validateNotNull(eventLogger);
        validateEquality("Foo", eventLogger.getName());
        validateEquality(testClassNamed_1.hashCode(), testClassNamed_2.hashCode());
    }
    public void testTestClassFinal()
    {
        TestClassFinal testClassFinal = new TestClassFinal();
        eventLoggerFactory.decorate(testClassFinal);
        validateNull(TestClassFinal.eventLogger);
    }
    public void testTestClassSealed()
    {
        TestClassSealed testClassSealed = eventLoggerFactory.decorate(TestClassSealed.class);
        validateNull(testClassSealed);
    }
    public void testTestClassWithAdHocFormatter()
    {
        TestClassAdHocFormatter testClassAdHocFormatter = eventLoggerFactory.decorate(TestClassAdHocFormatter.class);
        validateNotNull(testClassAdHocFormatter);
        EventLogger eventLogger = testClassAdHocFormatter.eventLogger;
        validateNotNull(eventLogger);
        validateEquality("AdHoc", eventLogger.getName());
        LogMessageFormatter logMessageFormatter = eventLogger.getLogMessageFormatter();
        validateNotNull(logMessageFormatter);
        validateTrue(logMessageFormatter instanceof AdHocLogMessageFormatter);
    }
}
