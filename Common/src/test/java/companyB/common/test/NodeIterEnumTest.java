package companyB.common.test;

import companyB.common.iter_enum.NodeIterEnum;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Test(groups = {"unit","node.iter.enum","common"})
public class NodeIterEnumTest
{
    private Iterator<String> iter_e;
    private Enumeration<String> e_iter;
    private String test_string;
    private String fake_string;
    private NodeIterEnum<String> nie;

    @BeforeMethod
    public void before() throws Exception
    {
        nie = new NodeIterEnum<>();
        e_iter = nie;
        iter_e = nie;
        test_string = "test string";
        fake_string = "fake string";
    }

    @AfterMethod
    public void after() throws Exception
    {
        iter_e = null;
        e_iter = null;
        test_string = null;
        fake_string = null;
        nie = null;
    }

    public void hasMoreElementsTrue()
    {
        nie.add(test_string);
        assertThat(e_iter.hasMoreElements(),is(true));
        assertThat(test_string,is(equalTo(e_iter.nextElement())));
    }

    public void hasMoreElementsTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        String next = e_iter.nextElement();
        assertThat(e_iter.hasMoreElements(),is(true));
        assertThat(test_string,is(equalTo(next)));
    }

    public void hasMoreElementsFalse()
    {
        assertThat(e_iter.hasMoreElements(),is(false));
        assertThat(e_iter.nextElement(),is(nullValue()));
    }

    public void hasMoreElementsFalseIterate()
    {
        nie.add(test_string);
        e_iter.nextElement();
        assertThat(e_iter.hasMoreElements(),is(false));
    }

    public void nextElementTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertThat(e_iter.nextElement(), is(equalTo(test_string)));
        assertThat(e_iter.nextElement(), is(equalTo(fake_string)));
        assertThat(e_iter.hasMoreElements(),is(false));
    }

    public void nextElementFalse()
    {
        nie.add(test_string);
        assertThat(e_iter.nextElement(), is(not(equalTo(fake_string))));
    }

    public void hasNextTrue()
    {
        nie.add(test_string);
        assertThat(iter_e.hasNext(),is(true));
    }

    public void hasNextTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        assertThat(iter_e.hasNext(),is(true));
    }

    public void hasNextFalse()
    {
        assertFalse(iter_e.hasNext());
    }

    public void hasNextFalseIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        iter_e.next();
        assertThat(iter_e.hasNext(),is(false));
    }

    public void nextTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertThat(iter_e.next(), is(equalTo(test_string)));
        assertThat(iter_e.next(), is(equalTo(fake_string)));
    }

    public void nextFalse()
    {
        nie.add(test_string);
        assertThat(iter_e.next(),is(not(equalTo(fake_string))));
    }

    public void add()
    {
        nie.add(test_string);
        assertThat(iter_e.hasNext(),is(true));
    }

    public void loadIter()
    {
        testStrings(true);
    }

    public void loadEnum()
    {
        testStrings(false);
    }
    public void remove()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.next();
        iter_e.remove();
        assertThat(iter_e.hasNext(),is(false));
        assertThat(iter_e.next(),is(nullValue()));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void removeNoInitialCallToNext()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.remove();
        fail("IllegalStateException should have been thrown - 'next' was not called before 'remove'.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void removeSubsequentCallToRemove()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        iter_e.remove();
        iter_e.remove();
        fail("IllegalStateException should have been thrown - 'next' was not called immediately before 'remove'.");
    }

    public void removeAfterAllGone()
    {
        nie.add(test_string);
        iter_e.next();
        iter_e.remove();
        assertThat(iter_e.hasNext(),is(false));
        assertThat(iter_e.next(),is(nullValue()));
    }

    private List<String> testStrings(Boolean testIterator)
    {
        final List<String>strings = new LinkedList<>();
        IntStream.range(0,1000).forEach((index)-> nie.add(String.format("String %s",index)));
        IntStream.range(0,1000).forEach((index)->{
            final String expected = String.format("String %s",index);
            final String actual = testIterator ? iter_e.next() : e_iter.nextElement();
            assertThat(expected, is(equalTo(actual)));
        });
        return strings;
    }
}