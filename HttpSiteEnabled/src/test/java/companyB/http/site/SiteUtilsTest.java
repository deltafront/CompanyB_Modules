package companyB.http.site;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;
@Test(groups = {"unit","site.utils","http.session.enabled"})
public class SiteUtilsTest
{

    private SiteUtils siteUtils;
    @BeforeMethod
    public void before()
    {
        siteUtils = new SiteUtils();
    }
    public void testGetLocalHostName()
    {
        String hostName = siteUtils.getHostName();
        assertNotNull(hostName);
        System.out.println(hostName);
    }

    public void testGetLocalIpAddress()
    {
        String ipAddress = siteUtils.getLocalIpAddress();
        assertNotNull(ipAddress);
        System.out.println(ipAddress);
    }

}
