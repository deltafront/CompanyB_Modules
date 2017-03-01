package companyB.common.utils;

import org.apache.commons.lang3.Validate;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Utilities for dealing with ServletRequests.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class ServletResponseUtils extends UtilityBase
{
    /**
     * Writes out a response to a ServletResponse's output stream.
     * @param response ServletResponse to be written to.
     * @param message Message to be written.
     * @param flush If this this true, then the response's output stream will be flushed and closed.
     */
    public void writeResponse(ServletResponse response, String message, boolean flush)
    {
        Validate.notNull(response, "ServletResponse must be provided!");
        final String cleaned_message = (null == message) ?
                "" : message;
        try(OutputStream outputStream  = response.getOutputStream();)
        {
            outputStream.write(cleaned_message.getBytes());
            if(flush)outputStream.flush();
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
