package companyB.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

/**
 * Provides a one-step utility for executing simple commands.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class RuntimeUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(RuntimeUtils.class);


    /**
     * Executes command and returns the result.
     * @param commandArgs Command line arguments
     * @return String result of Execution
     * @since 1.0
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
            StringBuilder stringBuilder = new StringBuilder("");
            while((temp = bufferedReader.readLine())!= null)
            {
                stringBuilder.append(temp);
            }
            stringBuilder.trimToSize();
            result = stringBuilder.toString();
        }
        catch (IOException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        LOGGER.trace(String.format("Result of command '%s': '%s'.", Arrays.toString(commandArgs), result));
        return result;
    }
}
