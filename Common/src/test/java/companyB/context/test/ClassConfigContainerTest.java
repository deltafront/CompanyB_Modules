package companyB.context.test;

import companyB.context.ClassArgsContainer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.fail;

@Test(groups = {"unit","class.config.container","context.enabled"})
public class ClassConfigContainerTest extends TestBase
{
    private Object[]argsArray = {42,"foo",false};
    private List<Object>argsList;
    String fqcn = TestObject.class.getCanonicalName();
    String id = "test.object";

    @BeforeMethod
    public void before()
    {
        argsList = new LinkedList<>();
        Collections.addAll(argsList,argsArray);
    }


    public void withList() throws ClassNotFoundException
    {
        ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,argsList,id);
        validateNotNull(classArgsContainer);
    }

    public void withArray() throws ClassNotFoundException
    {
        ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,argsArray,id);
        validateNotNull(classArgsContainer);
    }
    @Test(expectedExceptions = {ClassNotFoundException.class})
    public void withInvalidClassName() throws ClassNotFoundException
    {
        new ClassArgsContainer("foo",argsArray,id);
        fail("ClassNotFoundException should have been thrown.");
    }
}
