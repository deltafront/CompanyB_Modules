package companyB.http.cookie;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Reads a list of cookie definitions from a file. This file has to have both of the following:
 *
 * <ol>
 *      <li>
 *          Header row that contains the following:
 *          <ul>
 *              <li><pre>#name,value,domain,maxAge,path,secure,version,comment,httpOnly</pre></li>
 *          </ul>
 *      </li>
 *      <li>
 *          At least one row of data  that conforms to the following:
 *          <ol>
 *              <li>name (String)</li>
 *              <li>value (String)</li>
 *              <li>domain (String)</li>
 *              <li>maxAge (Integer)</li>
 *              <li>path (String)</li>
 *              <li>secure (Boolean, either 'TRUE' or 'FALSE')</li>
 *              <li>version (Integer, either '0' or '1')</li>
 *              <li>comment (String)</li>
 *              <li>httpOnly (Boolean, either 'TRUE' or 'FALSE')</li>
 *          </ol>
 *      </li>
 * </ol>
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class CookieFileReader
{
    private final static Logger LOGGER = LoggerFactory.getLogger(CookieFileReader.class);

    /**
     * Reads the cookie definitions from the file.
     * @param filename filename to read the cookie definitions from.
     * @return List of DefaultCookies.
     * @since 1.0
     */
    public List<DefaultCookie> readCookiesFromFile(String filename)
    {
        Validate.notEmpty(filename,"Filename must be provided.");
        final CookieUtils cookieUtils = new CookieUtils();
        final List<DefaultCookie> defaultCookies = new LinkedList<>();
        try
        {
            final File file = new File(filename);
            final List<String> lines = FileUtils.readLines(file);
            for (String line : lines)
            {
                if (0 != line.indexOf("#"))
                {
                    System.out.println(line);
                    final String[] vals = line.split(",");
                    System.out.println(vals.length);
                    Validate.isTrue(9 == vals.length);

                    final String name = vals[0];
                    final String value = vals[1];
                    final String domain = vals[2];
                    final String str_maxAge = vals[3];
                    final String path = vals[4];
                    final String str_secure = vals[5];
                    final String str_version = vals[6];
                    final String comment = vals[7];
                    final String str_httpOnly = vals[8];

                    validateStringValues(name, value, domain, path,comment);
                    validateBooleanValues(str_secure,str_httpOnly);
                    validateIntegerValues(str_maxAge,str_version);
                    validateVersion(str_version);


                    Cookie cookie = new Cookie(name, value);
                    cookie.setDomain(domain);
                    cookie.setPath(path);
                    cookie.setComment(comment);

                    setBooleanValues(cookie,str_secure,str_httpOnly);
                    setIntegerValues(cookie,str_maxAge,str_version);

                    defaultCookies.add(new DefaultCookie(cookie,true));
                    LOGGER.trace(String.format("Read cookie line from file '%s'\n%s",
                            file.getAbsoluteFile(), cookieUtils.cookieToString(cookie)));

                }
            }
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        LOGGER.debug(String.format("Returning %s cookies.",defaultCookies.size()));
        return defaultCookies;
    }

    private void validateStringValues(String name, String value, String domain, String path,String comment)
    {
        Validate.notBlank(name);
        Validate.notBlank(value);
        Validate.notBlank(domain);
        Validate.notBlank(path);
        Validate.notBlank(comment);
    }
    private void validateBooleanValues(String secure, String httpOnly)
    {
        Validate.notBlank(secure);
        verifyBoolean(secure);
        Validate.notBlank(httpOnly);
        verifyBoolean(httpOnly);
    }
    private void validateIntegerValues(String maxAge, String version)
    {
        Validate.notBlank(maxAge);
        Validate.notBlank(version);
    }
    private void setBooleanValues(Cookie cookie, String secure, String httpOnly)
    {
        cookie.setHttpOnly(Boolean.valueOf(httpOnly));
        cookie.setSecure(Boolean.valueOf(secure));
    }
    private void setIntegerValues(Cookie cookie,String maxAge, String version)
    {
        cookie.setVersion(Integer.valueOf(version));
        cookie.setMaxAge(Integer.valueOf(maxAge));
    }

    private void validateVersion(String str_version)
    {
        Validate.isTrue( "0".equals(str_version) || "1".equals(str_version));
    }
    private void verifyBoolean(String bool)
    {
        Validate.isTrue("false".equals(bool.toLowerCase()) || "true".equals(bool.toLowerCase()));
    }
}
