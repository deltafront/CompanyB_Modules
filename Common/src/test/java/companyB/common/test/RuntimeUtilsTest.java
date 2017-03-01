package companyB.common.test;

import companyB.common.utils.RuntimeUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@Test(groups = {"unit","runtime.utils","common","utils"})
public class RuntimeUtilsTest
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
        assertThat(out,is(not(nullValue())));
    }
    public void invalidCommand()
    {
        final String out = runtimeUtils.executeCommand("blah");
        assertThat(out,is(nullValue()));
    }
}
