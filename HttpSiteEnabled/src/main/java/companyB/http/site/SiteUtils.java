package companyB.http.site;

import companyB.common.utils.RuntimeUtils;
import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides host name and IP address.
 * @author Charles Burrell (deltafront@gmail.com).
 * @since 1.0.0
 */
public class SiteUtils extends UtilityBase
{
    @SuppressWarnings("unused")
    private boolean throwException;
    /**
     * Provides host name. It does so in the following order:
     * - It gets the host name attached to the local Inet Address
     * - If above is null and application is hosted on a  Windows box, it gets the 'COMPUTERNAME' entry
     * - If above is null and application is hosted on a  Linux-based box, it gets the 'HOSTNAME' entry
     * - If above is null, it executes the 'hostname' command
     * @return Hostname or null if it cannot be found.
     * @since 1.0.0
     */
    public String getHostName()
    {
        final RuntimeUtils runtimeUtils = new RuntimeUtils();
        InetAddress inetAddress = getLocalInetAddress();
        Validate.notNull(inetAddress);
        String inetAddressHostName = inetAddress.getHostName();
        String systemComputerHostName = System.getenv("COMPUTERNAME");
        String systemHostName = System.getenv("HOSTNAME");
        String runtimeHostName = runtimeUtils.executeCommand("hostname");
        String hostname = getFirstNonBlankString(inetAddressHostName,systemComputerHostName,
                systemHostName,runtimeHostName);
        LOGGER.debug(String.format("Returning hostname '%s'.", hostname));
        return hostname;
    }

    /**
     * @return Local IP Address.
     * @since 1.0.0
     */
    public String getLocalIpAddress()
    {
        InetAddress inetAddress = getLocalInetAddress();
        Validate.notNull(inetAddress);
        String ipAddress = inetAddress.getHostAddress();
        LOGGER.trace(String.format("Returning IP Address: %s", inetAddress));
        return ipAddress;

    }
    private InetAddress getLocalInetAddress()
    {
        InetAddress inetAddress = null;
        try
        {
            inetAddress = InetAddress.getLocalHost();
            if(throwException)throw new UnknownHostException("UnknownHostException thrown for test purposes.");
        }
        catch (UnknownHostException e)
        {
           LOGGER.error(e.getMessage(),e);
        }
        return inetAddress;
    }

    private String getFirstNonBlankString(String...strings)
    {
        String nonBlank = null;
        for(String string : strings)
        {
            if(StringUtils.isNotBlank(string) && null == nonBlank)
            {
                nonBlank = string;
            }
        }
        LOGGER.trace(String.format("Returning first non-blank value: %s",nonBlank));
        return nonBlank;
    }
}
