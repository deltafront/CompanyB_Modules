package companyB.http.site;

import companyB.common.utils.RuntimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides host name and IP address.
 * @author Charles Burrell (deltafront@gmail.com).
 * @version 1.0
 */
public class SiteUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SiteUtils.class);

    /**
     * Provides host name. It does so in the following order:
     * - It gets the host name attached to the local Inet Address
     * - If above is null and application is hosted on a  Windows box, it gets the 'COMPUTERNAME' entry
     * - If above is null and application is hosted on a  Linux-based box, it gets the 'HOSTNAME' entry
     * - If above is null, it executes the 'hostname' command
     * @return Hostname or null if it cannot be found.
     * @since 1.0
     */
    public String getHostName()
    {
        final RuntimeUtils runtimeUtils = new RuntimeUtils();
        String hostname = null;
        InetAddress inetAddress = getLocalInetAddress();
        Validate.notNull(inetAddress);
        hostname = inetAddress.getHostName();
        if (StringUtils.isBlank(hostname))
        {
            hostname = System.getenv("COMPUTERNAME");
        }
        if (StringUtils.isBlank(hostname))
        {
            hostname = System.getenv("HOSTNAME");
        }
        if(StringUtils.isBlank(hostname))
        {
           hostname = runtimeUtils.executeCommand("hostname");
        }
        LOGGER.debug(String.format("Returning hostname '%s'.", hostname));
        return hostname;
    }

    /**
     * @return Local IP Address.
     * @since 1.0
     */
    public String getLocalIpAddress()
    {
        InetAddress inetAddress = getLocalInetAddress();
        Validate.notNull(inetAddress);
        String ipAddress = inetAddress.getHostAddress();
        LOGGER.trace(String.format("Returning IP Address: %s", inetAddress));
        return ipAddress;

    }
    private static InetAddress getLocalInetAddress()
    {
        InetAddress inetAddress = null;
        try
        {
            inetAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        return inetAddress;
    }


}
