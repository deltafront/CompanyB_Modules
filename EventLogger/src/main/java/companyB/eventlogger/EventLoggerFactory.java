package companyB.eventlogger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Hashtable;

/**
 * Class responsible for providing singleton instances of EventLoggers to external clients.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class EventLoggerFactory
{
    private static Hashtable<Object, EventLogger> loggerMapping = new Hashtable<>();;
    private final static Logger LOGGER = LoggerFactory.getLogger(EventLoggerFactory.class);

    /**
     * @param name Name of Event Logger to get.
     * @return Singleton instance of Event Logger.
     * @since 2.0.0
     */
    public EventLogger getEventLogger(String name)
    {
        Validate.notBlank(name,"Event Logger name is required.");
        return getLogger(name);
    }

    /**
     * @param c Class to bind EventLogger to.
     * @return Singleton instance of Event Logger.
     * @since 2.0.0
     */
    public EventLogger getEventLogger(Class c)
    {
        Validate.notNull(c,"Logger class that EventLogger is to be bound to is required.");
        return getLogger(c.getCanonicalName());
    }
    /**
     * @param instance Instance of class to bind EventLogger to. Internally, the event logger is bound to the string representation
     *                 of the instance's hash code.
     * @return Singleton instance of Event Logger.
     * @since 2.0.0
     */
    public EventLogger getEventLogger(Object instance)
    {
        Validate.notNull(instance,"Instance of class for EventLogger to be bound to is required.");
        return getLogger(String.valueOf(instance.hashCode()));
    }

    /**
     * Returns an instance of class with EventLogger field decorated with an instance of EventLogger.
     * @param c Class to be instantiated and decorated. This class must have a zero-args constructor.
     * @param <T> Type parameter.
     * @return Decorated instance of class with EventLogger field decorated with an instance of EventLogger.
     */
    public <T>T decorate(Class<T> c)
    {
        T out = null;
        try
        {
            out = decorate(c.newInstance());
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }
    /**
     * Decorates an object's EventLogger field with an instance of EventLogger.
     * @param instance instance to be decorated.
     * @param <T> Type parameter.
     * @return Decorated instance.
     */
    public <T> T decorate(T instance)
    {
        Validate.notNull(instance,"Instance of Object that Event Logger is to be bound to is required.");
        try
        {
            Field[]fields = instance.getClass().getDeclaredFields();
            for(Field field : fields)
            {
                if(field.getType().equals(EventLogger.class))
                {
                    field.setAccessible(true);
                    EventLog eventLog = field.getAnnotation(EventLog.class);
                    if(null != eventLog)
                    {
                        String key = getName(eventLog,instance);
                        LogMessageFormatter logMessageFormatter = getLogMessageFormatter(eventLog);
                        EventLogger eventLogger = getLogger(key).withLogMessageFormatter(logMessageFormatter);
                        field.set(instance,eventLogger);
                        LOGGER.trace(String.format("Decorated field %s.%s (key=%s) with instance of EventLogger",
                                instance.getClass().getCanonicalName(),field.getName(),key));
                    }
                }
            }
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return instance;
    }

    private EventLogger getLogger(String key)
    {
        Validate.notNull(key,"EventLogger mapping key is required.");
        if (!loggerMapping.containsKey(key))
        {
            Logger logger = LoggerFactory.getLogger(key);
            loggerMapping.put(key, new EventLogger(logger, String.valueOf(key)));
        }
        LOGGER.trace(String.format("Returning instance of EventLogger bound to key '%s'.",key));
        return loggerMapping.get(key);
    }
    private String getName(EventLog eventLog, Object instance)
    {
        if (StringUtils.isEmpty(eventLog.name())) return instance.getClass().getCanonicalName();
        else return eventLog.name();
    }
    @SuppressWarnings("unchecked")
    private <T extends LogMessageFormatter> T getLogMessageFormatter(EventLog eventLog)
    {
        Class logMessageFormatClass = eventLog.logMessageFormatter();
        LogMessageFormatter out = new DefaultLotMessageFormatter();
        try
        {
            if (null != logMessageFormatClass)
            {
                out = (LogMessageFormatter)logMessageFormatClass.newInstance();
            }

        }
        catch (InstantiationException | IllegalAccessException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.trace(String.format("Returning instance of %s.",out.getClass().getCanonicalName()));
        return (T)out;
    }
}