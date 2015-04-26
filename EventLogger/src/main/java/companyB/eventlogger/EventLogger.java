package companyB.eventlogger;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import java.util.Properties;
/**
 * Logs event to underlying slf4j Facade.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class EventLogger
{
    /**
     * States that this event logger is in. Reflects type of last transaction logged.
     */
    public enum State
    {
        /* Logger has just been created and has not logged a message yet */
        INIT,
        /* Logger has last logged a message to 'trace' */
        TRACE,
        /* Logger has last logged a message to 'trace' */
        INFO,
        /* Logger has last logged a message to 'trace' */
        DEBUG,
        /* Logger has last logged a message to 'trace' */
        WARN,
        /* Logger has last logged a message to 'trace' */
        ERROR
    }
    private final Logger logger;
    private State state;
    private String lastMessage;
    private final String name;
    private LogMessageFormatter logMessageFormatter;

    EventLogger(Logger logger, String name)
    {
        Validate.notNull(logger,"SLF4J Logger is required.");
        Validate.notNull(name,"Logger name is required.");
        this.logger = logger;
        this.state = State.INIT;
        this.name = name;
        this.logMessageFormatter = new DefaultLotMessageFormatter();
    }

    /**
     * @param logMessageFormatter Implementation of LogMessageFormatter to be used.
     * @return Instance of EventLogger using specified LogMessageFormatter.
     * @since 2.0.0
     */
    public EventLogger withLogMessageFormatter(LogMessageFormatter logMessageFormatter)
    {
        Validate.notNull(logMessageFormatter,"LogMessageFormatter is required.");
        this.logMessageFormatter = logMessageFormatter;
        return this;
    }

    /**
     * Logs this event to TRACE.
     * @param eventCode Event Code for this message.
     * @param message Message to be logged.
     * @param throwable Throwable to attach to message, can be null.
     * @param attributes Attributes to attach to this message, can be null.
     * @since 2.0.0
     */
    public void trace(EventCode eventCode, String message, Throwable throwable, Properties attributes)
    {
        log(eventCode,message,attributes,throwable,State.TRACE);
    }
    /**
     * Logs this event to WARN.
     * @param eventCode Event Code for this message.
     * @param message Message to be logged.
     * @param throwable Throwable to attach to message, can be null.
     * @param attributes Attributes to attach to this message, can be null.
     * @since 2.0.0
     */
    public void warn(EventCode eventCode, String message, Throwable throwable, Properties attributes)
    {
        log(eventCode,message,attributes,throwable,State.WARN);
    }
    /**
     * Logs this event to DEBUG.
     * @param eventCode Event Code for this message.
     * @param message Message to be logged.
     * @param throwable Throwable to attach to message, can be null.
     * @param attributes Attributes to attach to this message, can be null.
     * @since 2.0.0
     */
    public void debug(EventCode eventCode, String message, Throwable throwable, Properties attributes)
    {
        log(eventCode,message,attributes,throwable,State.DEBUG);
    }
    /**
     * Logs this event to INFO.
     * @param eventCode Event Code for this message.
     * @param message Message to be logged.
     * @param throwable Throwable to attach to message, can be null.
     * @param attributes Attributes to attach to this message, can be null.
     * @since 2.0.0
     */
    public void info(EventCode eventCode, String message, Throwable throwable, Properties attributes)
    {
        log(eventCode,message,attributes,throwable,State.INFO);
    }
    /**
     * Logs this event to ERROR.
     * @param eventCode Event Code for this message.
     * @param message Message to be logged.
     * @param throwable Throwable to attach to message, can be null.
     * @param attributes Attributes to attach to this message, can be null.
     * @since 2.0.0
     */
    public void error(EventCode eventCode, String message, Throwable throwable, Properties attributes)
    {
        log(eventCode,message,attributes,throwable,State.ERROR);
    }

    /**
     * @return Current state of this EventLogger, which is the level of the last message logged.
     * @since 2.0.0
     */
    public State getState()
    {
        return state;
    }

    /**
     * @return Name of this EventLogger.
     * @since 2.0.0
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Last message logged by this EventLogger.
     * @since 2.0.0
     */
    public String getLastMessage()
    {
        return lastMessage;
    }

    /**
     * @return LogMessageFormatter used by this EventLogger.
     * @since 2.0.0
     */
    public LogMessageFormatter getLogMessageFormatter()
    {
        return logMessageFormatter;
    }


    private void log(EventCode eventCode, String message, Properties attributes, Throwable throwable, State state)
    {
        this.state = state;
        this.lastMessage = logMessageFormatter.formatLogMessage(eventCode,message,attributes);
        switch (state)
        {
            case TRACE:
                if(null == throwable) logger.trace(this.lastMessage);
                else logger.trace(this.lastMessage,throwable);
                break;
            case INFO:
                if(null == throwable) logger.info(lastMessage);
                else logger.info(lastMessage, throwable);
                break;
            case DEBUG:
                if(null == throwable) logger.debug(lastMessage);
                else logger.debug(lastMessage, throwable);
                break;
            case WARN:
                if(null == throwable) logger.warn(lastMessage);
                else logger.warn(lastMessage, throwable);
                break;
            case ERROR:
                if(null == throwable) logger.error(lastMessage);
                else logger.error(lastMessage, throwable);
                break;
            case INIT:
            default:
                break;
        }
    }


}
