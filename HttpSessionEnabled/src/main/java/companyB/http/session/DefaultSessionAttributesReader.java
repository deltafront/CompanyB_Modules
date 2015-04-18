package companyB.http.session;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Reads Default Attributes from a text file. The file must contain one line adhering to the following:
 * - If maxInterval is to be defined:
 * 42=foo,bar,one,two
 * - If maxInterval is not to be defined:
 * foo,bar,one,two
 * Created by Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class DefaultSessionAttributesReader
{
    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultSessionAttributesReader.class);

    /**
     * Reads default session attributes from a file.
     * @param filename path to default session attributes file.
     * @return DefaultSessionAttributes object.
     * @since 1.0
     */
    public DefaultSessionAttributes readDefaultSessionAttributes(String filename)
    {
        Validate.notBlank(filename);
        DefaultSessionAttributes defaultSessionAttributes = null;
        final File file = new File(filename);
        LOGGER.trace(String.format("Reading attributes from %s",file.getAbsolutePath()));
        try
        {
            final Reader reader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(reader);
            String temp;
            while((temp = bufferedReader.readLine())!= null)
            {
                defaultSessionAttributes = getDefaultSessionAttributes(temp);
                LOGGER.trace(defaultSessionAttributes.toString());
            }
            bufferedReader.close();
            reader.close();
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }

        return defaultSessionAttributes;
    }

    /**
     * Reads default session attributes from a line. Useful if the default session attributes are stored in a properties file.
     * @param line Line containing the default session attributes.
     * @return DefaultSessionAttributes object.
     * @since 1.0
     */
    public DefaultSessionAttributes getDefaultSessionAttributes(String line)
    {
        Validate.notBlank(line);
        LOGGER.trace(String.format("Getting DefaultSessionAttributes from line:\n%s",line));
        final List<String> attributeList = new LinkedList<>();
        Integer maxInterval = -1;
        String[]attributes;
        if(line.contains("="))
        {
            final String[]splits = line.split("=");
            maxInterval = Integer.parseInt(splits[0]);
            attributes = splits[1].contains(",") ? splits[1].split(",") : new String[]{splits[1]};

        }
        else if (line.contains(","))
        {
            attributes = line.split(",");
        }
        else
        {
            attributes = new String[]{line};
        }
        Collections.addAll(attributeList, attributes);
        DefaultSessionAttributes defaultSessionAttributes = new DefaultSessionAttributes(attributeList);
        defaultSessionAttributes = defaultSessionAttributes.withMaxInterval(maxInterval);
        LOGGER.trace(defaultSessionAttributes.toString());
        return defaultSessionAttributes;
    }
}
