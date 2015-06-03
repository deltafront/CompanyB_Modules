package companyB.companyB.eventlogger.test;

import companyB.eventlogger.*;
import junit.framework.TestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","event.logger","event.logger.factory"})
public class EventLoggerFactoryTest
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
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        assertEquals(name,eventLogger.getName());
    }
    public void testWithValidStringNameTwoInstances()
    {
        String name = "foo.com";
        EventLogger eventLogger_1 = eventLoggerFactory.getEventLogger(name);
        EventLogger eventLogger_2 = eventLoggerFactory.getEventLogger(name);
        assertNotNull(eventLogger_1);
        assertNotNull(eventLogger_2);
        assertEquals(EventLogger.State.INIT, eventLogger_1.getState());
        assertEquals(EventLogger.State.INIT, eventLogger_2.getState());
        assertEquals(name,eventLogger_1.getName());
        assertEquals(name,eventLogger_2.getName());
        assertEquals(eventLogger_1.hashCode(), eventLogger_2.hashCode());
    }
    public void testWithValidClass()
    {
        Class c = this.getClass();
        eventLogger = eventLoggerFactory.getEventLogger(c);
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        TestCase.assertEquals(c.getCanonicalName(),eventLogger.getName());
    }
    public void testWithValidObjectInstance()
    {
        Integer instance = -1;
        String hashCode = String.valueOf(instance.hashCode());
        eventLogger = eventLoggerFactory.getEventLogger(instance);
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        TestCase.assertEquals(hashCode,eventLogger.getName());
    }
    public void testSingletonInstances()
    {
        EventLogger eventLogger1 = eventLoggerFactory.getEventLogger(this.getClass());
        EventLogger eventLogger2 = eventLoggerFactory.getEventLogger(this.getClass());
        assertNotNull(eventLogger1);
        assertNotNull(eventLogger2);
        assertEquals(eventLogger1.hashCode(), eventLogger2.hashCode());

    }
    public void testTestClassDefaultNotInstantiated()
    {
        TestClassDefault testClassDefault = eventLoggerFactory.decorate(TestClassDefault.class);
        assertNotNull(testClassDefault);
        EventLogger eventLogger = testClassDefault.eventLogger;
        assertNotNull(eventLogger);
        assertEquals(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
        LogMessageFormatter logMessageFormatter = eventLogger.getLogMessageFormatter();
        assertNotNull(logMessageFormatter);
        assertTrue(logMessageFormatter instanceof DefaultLotMessageFormatter);
    }
    public void testTestClassDefaultInstantiated()
    {
        TestClassDefault testClassDefault_1 = new TestClassDefault();
        TestClassDefault testClassDefault_2 = eventLoggerFactory.decorate(testClassDefault_1);
        assertNotNull(testClassDefault_2);
        EventLogger eventLogger = testClassDefault_2.eventLogger;
        assertNotNull(eventLogger);
        assertEquals(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
        assertEquals(testClassDefault_1.hashCode(), testClassDefault_2.hashCode());
    }
    public void testTestClassNamedNotInstantiated()
    {
        TestClassNamed testClassNamed = eventLoggerFactory.decorate(TestClassNamed.class);
        assertNotNull(testClassNamed);
        EventLogger eventLogger = testClassNamed.eventLogger;
        assertNotNull(eventLogger);
        TestCase.assertEquals("Foo", eventLogger.getName());
    }
    public void testTestClassNamedInstantiated()
    {
        TestClassNamed testClassNamed_1 = new TestClassNamed();
        TestClassNamed testClassNamed_2 = eventLoggerFactory.decorate(testClassNamed_1);
        assertNotNull(testClassNamed_2);
        EventLogger eventLogger = testClassNamed_2.eventLogger;
        assertNotNull(eventLogger);
        assertEquals("Foo", eventLogger.getName());
        assertEquals(testClassNamed_1.hashCode(), testClassNamed_2.hashCode());
    }
    public void testTestClassFinal()
    {
        TestClassFinal testClassFinal = new TestClassFinal();
        eventLoggerFactory.decorate(testClassFinal);
        assertNull(TestClassFinal.eventLogger);
    }
    public void testTestClassSealed()
    {
        TestClassSealed testClassSealed = eventLoggerFactory.decorate(TestClassSealed.class);
        assertNull(testClassSealed);
    }
    public void testTestClassWithAdHocFormatter()
    {
        TestClassAdHocFormatter testClassAdHocFormatter = eventLoggerFactory.decorate(TestClassAdHocFormatter.class);
        assertNotNull(testClassAdHocFormatter);
        EventLogger eventLogger = testClassAdHocFormatter.eventLogger;
        assertNotNull(eventLogger);
        assertEquals("AdHoc", eventLogger.getName());
        LogMessageFormatter logMessageFormatter = eventLogger.getLogMessageFormatter();
        assertNotNull(logMessageFormatter);
        assertTrue(logMessageFormatter instanceof AdHocLogMessageFormatter);
    }
}
