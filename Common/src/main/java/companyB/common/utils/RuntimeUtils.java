package companyB.common.utils;

import java.io.*;
import java.util.Arrays;

/**
 * Provides a one-step utility for executing simple commands.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since  1.0.0
 */
public class RuntimeUtils extends UtilityBase
{

    /**
     * Executes command and returns the result.
     * @param commandArgs Command line arguments
     * @return String result of Execution
     * @since 1.0.0
     */
    public String executeCommand(String... commandArgs)
    {
        String result = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(commandArgs);
            InputStream inputStream = process.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String temp;
            while((temp = bufferedReader.readLine())!= null) result += temp;
        }
        catch (IOException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        LOGGER.trace(String.format("Result of command '%s': '%s'.", Arrays.toString(commandArgs), result));
        return result;
    }
}
