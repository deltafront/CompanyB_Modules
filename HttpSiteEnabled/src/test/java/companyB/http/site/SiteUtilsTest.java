package companyB.http.site;

import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;
@Test(groups = {"unit","site.utils","http.session.enabled"})
public class SiteUtilsTest
{

    public void testGetLocalHostName()
    {
        String hostName = SiteUtils.getHostName();
        assertNotNull(hostName);
        System.out.println(hostName);
    }

    public void testGetLocalIpAddress()
    {
        String ipAddress = SiteUtils.getLocalIpAddress();
        assertNotNull(ipAddress);
        System.out.println(ipAddress);
    }

}
