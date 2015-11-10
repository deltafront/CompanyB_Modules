package companyB.common.utils;

import javax.servlet.ServletRequest;
import java.io.*;

/**
 * Utility class for dealing with ServletRequests.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0
 */
public class ServletRequestUtils extends UtilityBase
{
    /**
     * Gets the body of the request and returns it as a string.
     * @param request ServletRequest to get the body from.
     * @return Body of request, or null if none present.
     */
    public String getBodyFromRequest(ServletRequest request )
    {
        String result = "";
        try
        {
            final BufferedReader bufferedReader = request.getReader();
            String temp;
            while(null !=(temp = bufferedReader.readLine()))
            {
                result += temp;
            }
            if(0 == result.length())result = null;
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        LOGGER.trace(String.format("Returning result to client:\n%s", result));
        return result;
    }
}
