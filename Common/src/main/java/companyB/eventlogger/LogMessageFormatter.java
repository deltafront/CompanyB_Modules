package companyB.eventlogger;

import java.util.Properties;

/**
 * Defines contract for formatting log messages. Concrete implementations should define custom formatting for the log messages.
 * @author Charles Burrell (deltafront@gmail.com)
 *
 */
public interface LogMessageFormatter
{
    /**
     * Returns the properly formatted log message.
     * @param eventCode Event Code for this log message.
     * @param message Message to be included.
     * @param attributes Properties that contain various attributes to be logged.
     * @return Formatted log message
     */
    String formatLogMessage(EventCode eventCode, String message, Properties attributes);
}
