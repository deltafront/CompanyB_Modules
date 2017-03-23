package companyB.eventlogger;

import org.apache.commons.lang3.Validate;

import java.util.Properties;

/**
 * Default implementation of LogMessageFactory. Messages will be formatted to look like "Event=TestEventCode (42) {this=that,message=this is a test message}".
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class DefaultLogMessageFormatter implements LogMessageFormatter
{
    @Override
    public String formatLogMessage(EventCode eventCode, String message, Properties attributes)
    {
        validateInputs(eventCode, message, attributes);
        final StringBuilder attributesMessage = new StringBuilder("{");
        attributes.stringPropertyNames().forEach((key)->
                attributesMessage.append(String.format("%s=%s,",key,attributes.getProperty(key))));
        attributesMessage.append(String.format("message=%s}",message));
        return String.format("%s %s", eventCode.toString(),attributesMessage);
    }

    private void validateInputs(EventCode eventCode, String message, Properties attributes)
    {
        Validate.notNull(attributes,"Attributes are required!");
        Validate.notNull(eventCode,"Event Code is required.");
        Validate.notNull(message,"Message for this event is required.");
    }
}
