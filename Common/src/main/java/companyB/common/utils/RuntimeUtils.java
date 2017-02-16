package companyB.common.utils;

import org.apache.commons.lang3.Validate;

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
        try(final InputStream inputStream = getProcess(commandArgs).getInputStream();
            final Reader reader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(reader)
        )
        {
            String temp;
            while((temp = bufferedReader.readLine())!= null) result += temp;
        }
        catch (IOException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        LOGGER.trace("Result of command '{}': '{}'.", Arrays.toString(commandArgs), result);
        return result;
    }
    private Process getProcess(String...commandArgs)
    {
        Process process = null;
        try
        {
            final Runtime runtime = Runtime.getRuntime();
            process = runtime.exec(commandArgs);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        Validate.notNull(process,"Null Process returned by system runtime!");
        return process;
    }
}
