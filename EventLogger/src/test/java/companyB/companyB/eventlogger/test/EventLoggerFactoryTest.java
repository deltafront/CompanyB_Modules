package companyB.companyB.eventlogger.test;

import companyB.eventlogger.EventLog;
import companyB.eventlogger.EventLogger;
import companyB.eventlogger.EventLoggerFactory;
import junit.framework.TestCase;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","event.logger","event.logger.factory"})
public class EventLoggerFactoryTest
{
    private EventLogger eventLogger;
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

    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullString()
    {
        String nullString = null;
        eventLogger = EventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullClass()
    {
        Class nullString = null;
        eventLogger = EventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    @Test(expectedExceptions = {NullPointerException.class})
    public void testInitWithNullObject()
    {
        Object nullString = null;
        eventLogger = EventLoggerFactory.getEventLogger(nullString);
        fail("NullPointerException should have been thrown.");
    }
    public void testWithValidStringName()
    {
        String name = "foo.com";
        eventLogger = EventLoggerFactory.getEventLogger(name);
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        assertEquals(name,eventLogger.getName());
    }
    public void testWithValidStringNameTwoInstances()
    {
        String name = "foo.com";
        EventLogger eventLogger_1 = EventLoggerFactory.getEventLogger(name);
        EventLogger eventLogger_2 = EventLoggerFactory.getEventLogger(name);
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
        eventLogger = EventLoggerFactory.getEventLogger(c);
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        TestCase.assertEquals(c.getCanonicalName(),eventLogger.getName());
    }
    public void testWithValidObjectInstance()
    {
        Integer instance = -1;
        String hashCode = String.valueOf(instance.hashCode());
        eventLogger = EventLoggerFactory.getEventLogger(instance);
        assertNotNull(eventLogger);
        assertEquals(EventLogger.State.INIT, eventLogger.getState());
        TestCase.assertEquals(hashCode,eventLogger.getName());
    }
    public void testSingletonInstances()
    {
        EventLogger eventLogger1 = EventLoggerFactory.getEventLogger(this.getClass());
        EventLogger eventLogger2 = EventLoggerFactory.getEventLogger(this.getClass());
        assertNotNull(eventLogger1);
        assertNotNull(eventLogger2);
        assertEquals(eventLogger1.hashCode(), eventLogger2.hashCode());

    }
    public void testTestClassDefaultNotInstantiated()
    {
        TestClassDefault testClassDefault = EventLoggerFactory.decorate(TestClassDefault.class);
        assertNotNull(testClassDefault);
        EventLogger eventLogger = testClassDefault.eventLogger;
        assertNotNull(eventLogger);
        TestCase.assertEquals(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
    }
    public void testTestClassDefaultInstantiated()
    {
        TestClassDefault testClassDefault_1 = new TestClassDefault();
        TestClassDefault testClassDefault_2 = EventLoggerFactory.decorate(testClassDefault_1);
        assertNotNull(testClassDefault_2);
        EventLogger eventLogger = testClassDefault_2.eventLogger;
        assertNotNull(eventLogger);
        assertEquals(TestClassDefault.class.getCanonicalName(), eventLogger.getName());
        assertEquals(testClassDefault_1.hashCode(), testClassDefault_2.hashCode());
    }
    public void testTestClassNamedNotInstantiated()
    {
        TestClassNamed testClassNamed = EventLoggerFactory.decorate(TestClassNamed.class);
        assertNotNull(testClassNamed);
        EventLogger eventLogger = testClassNamed.eventLogger;
        assertNotNull(eventLogger);
        TestCase.assertEquals("Foo", eventLogger.getName());
    }
    public void testTestClassNamedInstantiated()
    {
        TestClassNamed testClassNamed_1 = new TestClassNamed();
        TestClassNamed testClassNamed_2 = EventLoggerFactory.decorate(testClassNamed_1);
        assertNotNull(testClassNamed_2);
        EventLogger eventLogger = testClassNamed_2.eventLogger;
        assertNotNull(eventLogger);
        assertEquals("Foo", eventLogger.getName());
        assertEquals(testClassNamed_1.hashCode(), testClassNamed_2.hashCode());
    }
}
