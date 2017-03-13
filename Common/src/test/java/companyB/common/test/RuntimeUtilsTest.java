package companyB.common.test;

import companyB.common.utils.RuntimeUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = {"unit","runtime.utils","common","utils"})
public class RuntimeUtilsTest extends TestBase
{
    private RuntimeUtils runtimeUtils;
    @BeforeMethod
    public void before()
    {
        runtimeUtils = new RuntimeUtils();
    }
    public void runSimpleCommand()
    {
        final String out = runtimeUtils.executeCommand("date");
        validateNotNull(out);
    }
    public void invalidCommand()
    {
        final String out = runtimeUtils.executeCommand("blah");
        validateNull(out);
    }
}
