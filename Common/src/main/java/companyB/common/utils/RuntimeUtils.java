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
        final StringBuilder result = new StringBuilder();
        try
        {
            final Optional<Process>process = getProcess(commandArgs);
            if(process.isPresent())
            {
                try(final InputStream inputStream = process.get().getInputStream();
                    final Reader reader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(reader)
                )
                {
                    bufferedReader.lines().forEach(result::append);
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
        return result.toString().length() ==0 ? null : result.toString();
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
