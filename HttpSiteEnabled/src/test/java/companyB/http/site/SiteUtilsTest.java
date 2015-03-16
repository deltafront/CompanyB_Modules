package companyB.http.site;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class SiteUtilsTest
{
    @Test
    public void testGetLocalHostName()
    {
        String hostName = SiteUtils.getHostName();
        assertNotNull(hostName);
        System.out.println(hostName);
    }
    @Test
    public void testGetLocalIpAddress()
    {
        String ipAddress = SiteUtils.getLocalIpAddress();
        assertNotNull(ipAddress);
        System.out.println(ipAddress);
    }

}
