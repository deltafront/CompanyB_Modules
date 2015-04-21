package companyB.context.test;

import companyB.context.ClassArgsContainer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
@Test(groups = {"unit","class.config.container","context.enabled"})
public class ClassConfigContainerTest
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
        assertNotNull(classArgsContainer);
    }

    public void withArray() throws ClassNotFoundException
    {
        ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,argsArray,id);
        assertNotNull(classArgsContainer);
    }
    @Test(expectedExceptions = {ClassNotFoundException.class})
    public void withInvalidClassName() throws ClassNotFoundException
    {
        new ClassArgsContainer("foo",argsArray,id);
        fail("ClassNotFoundException should have been thrown.");
    }
}
