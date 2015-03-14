package companyB.common;

import companyB.common.utils.RuntimeUtils;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class RuntimeUtilsTest
{
    @Test
    public void testRunSimpleCommand()
    {
        String out = RuntimeUtils.executeCommand("date");
        assertNotNull(out);
        System.out.println(out);
    }
}
