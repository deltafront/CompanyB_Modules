package companyB.common;

import companyB.common.utils.RuntimeUtils;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertNotNull;

@Test(groups = {"unit","runtime.utils","common"})
public class RuntimeUtilsTest
{
    public void testRunSimpleCommand()
    {
        String out = RuntimeUtils.executeCommand("date");
        assertNotNull(out);
        System.out.println(out);
    }
}
