package companyB.common.utils;

import java.io.*;
import java.util.Optional;

/**
 * Provides a one-step utility for executing simple commands.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public class RuntimeUtils extends UtilityBase
{

    /**
     * Executes command and returns the result.
     * @param commandArgs Command line arguments
     * @return String result of Execution
     */
    @SuppressWarnings("ThrowFromFinallyBlock")
    public String executeCommand(String... commandArgs)
    {
        String result = null;
        try
        {
            final Optional<Process>process = getProcess(commandArgs);
            BufferedReader bufferedReader = null;
            if(process.isPresent())
            {
                try
                {
                    final InputStream inputStream = process.get().getInputStream();
                    final Reader reader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(reader);
                    String temp;
                    while((temp = bufferedReader.readLine())!= null) result += temp;
                }
                finally
                {
                    if(null != bufferedReader)
                        bufferedReader.close();
                }
            }
            else
            {
                throw new RuntimeUtilsException("Null process returned.");
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(),e);
        }

        return result;
    }
    private Optional<Process> getProcess(String...commandArgs)
    {
        Optional<Process>process;
        final Runtime runtime = Runtime.getRuntime();
        try
        {
            process = Optional.ofNullable(runtime.exec(commandArgs));
        }
        catch (Exception e)
        {
            process = Optional.empty();
        }
        return process;
    }
}

/**
 * Custom Exception thrown in case of process or runtime failure.
 */
class RuntimeUtilsException extends Exception
{
    RuntimeUtilsException(String message)
    {
        super(message);
    }

}
