package companyB.context.test;

import companyB.context.ClassArgsContainer;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class ClassConfigContainerTest
{
    private Object[]argsArray = {42,"foo",false};
    private List<Object>argsList;
    String fqcn = TestObject.class.getCanonicalName();
    String id = "test.object";

    @Before
    public void before()
    {
        argsList = new LinkedList<>();
        Collections.addAll(argsList,argsArray);
    }

    @Test
    public void withList() throws ClassNotFoundException
    {
        ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,argsList,id);
        assertNotNull(classArgsContainer);
    }
    @Test
    public void withArray() throws ClassNotFoundException
    {
        ClassArgsContainer classArgsContainer = new ClassArgsContainer(fqcn,argsArray,id);
        assertNotNull(classArgsContainer);
    }
    @Test(expected = ClassNotFoundException.class)
    public void withInvalidClassName() throws ClassNotFoundException
    {
        new ClassArgsContainer("foo",argsArray,id);
        fail("ClassNotFoundException should have been thrown.");
    }
}
