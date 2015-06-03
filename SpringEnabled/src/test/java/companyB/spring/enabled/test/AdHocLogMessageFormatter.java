package companyB.spring.enabled.test;

import companyB.eventlogger.EventCode;
import companyB.eventlogger.LogMessageFormatter;

import java.util.Properties;

public class AdHocLogMessageFormatter implements LogMessageFormatter
{
    @Override
    public String formatLogMessage(EventCode eventCode, String message, Properties attributes)
    {
        return null;
    }
}
