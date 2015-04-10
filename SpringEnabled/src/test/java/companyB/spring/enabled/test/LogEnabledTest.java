package companyB.spring.enabled.test;

import companyB.spring.log.Log;
import companyB.spring.log.LogBeanProcessor;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

@Test(groups = {"unit","spring.enabled","log.bean.processor"})
public class LogEnabledTest
{
    private LogBeanProcessor logBeanProcessor;
    @BeforeMethod
    public void before()
    {
        logBeanProcessor = new LogBeanProcessor();
    }
    public void testBefore()
    {
        TestLoggerClass testClass = new TestLoggerClass();
        testClass = (TestLoggerClass)logBeanProcessor.postProcessBeforeInitialization(testClass,"testClass");
        assertNotNull(testClass);
        Logger fooLogger = testClass.fooLogger;
        assertNotNull(fooLogger);
        Logger defaultLogger = testClass.defaultLogger;
        assertNotNull(defaultLogger);
        assertEquals(fooLogger.hashCode(),defaultLogger.hashCode());
        assertNull(testClass.nullLogger);
    }
    public void testAfter()
    {
        TestLoggerClass testClass = new TestLoggerClass();
        testClass = (TestLoggerClass)logBeanProcessor.postProcessAfterInitialization(testClass, "testClass");
        assertNotNull(testClass);
        Logger fooLogger = testClass.fooLogger;
        assertNull(fooLogger);
        Logger defaultLogger = testClass.defaultLogger;
        assertNull(defaultLogger);
    }
}
class TestLoggerClass
{
    @Log(name = "foo")
    public Logger fooLogger;

    @Log()
    public Logger defaultLogger;

    @Log
    public Object nullLogger;
}
