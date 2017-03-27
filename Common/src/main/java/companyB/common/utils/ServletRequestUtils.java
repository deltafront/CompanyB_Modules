package companyB.common.utils;

import javax.servlet.ServletRequest;
import java.io.*;

/**
 * Utility class for dealing with ServletRequests.
 * @author Charles Burrell (deltafront@gmail.com)
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
        final StringBuilder result = new StringBuilder();
        try(BufferedReader bufferedReader = request.getReader())
        {
            bufferedReader.lines().forEach(result::append);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return result.toString().length() ==0 ? null : result.toString();
    }
}
