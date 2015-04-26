package companyB.eventlogger;

import org.apache.commons.lang3.Validate;

import java.util.Properties;

/**
 * Default implementation of LogMessageFactory. Messages will be formatted to look like "Event=TestEventCode (42) {this=that,message=this is a test message}".
 * @author Charles Burrell (deltafront@gmail.com)
 * @since  2.0.0
 */
public class DefaultLotMessageFormatter implements LogMessageFormatter
{
    @Override
    public String formatLogMessage(EventCode eventCode, String message, Properties attributes)
    {
        if(null == attributes) attributes = new Properties();
        Validate.notNull(eventCode,"Event Code is required.");
        Validate.notNull(message,"Message for this event is required.");
        String attributesMessage = "{";
        for(final String key : attributes.stringPropertyNames())
            attributesMessage += String.format("%s=%s,",key,attributes.getProperty(key));
        attributesMessage += String.format("message=%s}",message);
        return String.format("%s %s", eventCode.toString(),attributesMessage);
    }
}
