package companyB.common;

import companyB.common.utils.RuntimeUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","runtime.utils","common"})
public class RuntimeUtilsTest
{
    private RuntimeUtils runtimeUtils;
    @BeforeMethod
    public void before()
    {
        runtimeUtils = new RuntimeUtils();
    }
    public void testRunSimpleCommand()
    {
        String out = runtimeUtils.executeCommand("date");
        assertNotNull(out);
    }
}
