package companyB.http.site;

import companyB.common.utils.FieldUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","site.utils","http.site.enabled"})
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
    }

    public void testGetLocalIpAddress()
    {
        String ipAddress = siteUtils.getLocalIpAddress();
        assertNotNull(ipAddress);
    }
    public void testGetLocalIpAddressWithException()
    {
        FieldUtils fieldUtils = new FieldUtils();
        Field throwException = fieldUtils.getField("throwException",siteUtils);
        assertNotNull(throwException);
        fieldUtils.setField(throwException,siteUtils,true);
        String ipAddress = siteUtils.getLocalIpAddress();
        assertNotNull(ipAddress);
    }
}
