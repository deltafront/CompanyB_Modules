package companyB.common.test;

import companyB.common.iter_enum.NodeIterEnum;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Enumeration;
import java.util.Iterator;

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
    public void setUp() throws Exception
    {
        nie = new NodeIterEnum<>();
        e_iter = nie;
        iter_e = nie;
        test_string = "test string";
        fake_string = "fake string";
    }

    @AfterMethod
    public void tearDown() throws Exception
    {
        iter_e = null;
        e_iter = null;
        test_string = null;
        fake_string = null;
        nie = null;
    }

    public void testHasMoreElementsTrue()
    {
        nie.add(test_string);
        assertTrue(e_iter.hasMoreElements());
        assertEquals(test_string, e_iter.nextElement());
    }

    public void testHasMoreElementsTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        String next = e_iter.nextElement();
        assertTrue(e_iter.hasMoreElements());
        assertEquals(test_string, next);
    }

    public void testHasMoreElementsFalse()
    {
        assertFalse(e_iter.hasMoreElements());
        assertNull(e_iter.nextElement());
    }

    public void testHasMoreElementsFalseIterate()
    {
        nie.add(test_string);
        e_iter.nextElement();
        assertFalse(e_iter.hasMoreElements());
    }

    public void testNextElementTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertEquals(e_iter.nextElement(), test_string);
        assertEquals(e_iter.nextElement(), fake_string);
        assertFalse(e_iter.hasMoreElements());
    }

    public void testNextElementFalse()
    {
        nie.add(test_string);
        assertFalse(e_iter.nextElement().equals(fake_string));
    }

    public void testHasNextTrue()
    {
        nie.add(test_string);
        assertTrue(iter_e.hasNext());
    }

    public void testHasNextTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        assertTrue(iter_e.hasNext());
    }

    public void testHasNextFalse()
    {
        assertFalse(iter_e.hasNext());
    }

    public void testHasNextFalseIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        iter_e.next();
        assertFalse(iter_e.hasNext());
    }

    public void testNextTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertEquals(iter_e.next(), test_string);
        assertEquals(iter_e.next(), fake_string);
    }

    public void testNextFalse()
    {
        nie.add(test_string);
        assertFalse(iter_e.next().equals(fake_string));
    }

    public void testAdd()
    {
        nie.add(test_string);
        assertTrue(iter_e.hasNext());
    }

    public void loadIter()
    {
        String[] strings = getStrings();
        for (String string : strings)
        {
            nie.add(string);
        }
        int count = 0;
        while (iter_e.hasNext())
        {
            assertEquals(strings[count], iter_e.next());
            count++;
        }
        assertEquals(strings.length, count);
    }

    public void loadEnum()
    {
        String[] strings = getStrings();
        for (String string : strings)
        {
            nie.add(string);
        }
        int count = 0;
        while (e_iter.hasMoreElements())
        {
            assertEquals(strings[count], e_iter.nextElement());
            count++;
        }
        assertEquals(strings.length, count);
    }
    public void testRemove()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.next();
        iter_e.remove();
        assertFalse(iter_e.hasNext());
        assertNull(iter_e.next());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testRemoveNoInitialCallToNext()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.remove();
        fail("IllegalStateException should have been thrown - 'next' was not called before 'remove'.");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testRemoveSubsequentCallToRemove()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.next();
        iter_e.remove();
        iter_e.remove();
        fail("IllegalStateException should have been thrown - 'next' was not called immediately before 'remove'.");
    }

    public void testRemoveAfterAllGone()
    {
        nie.add(test_string);
        iter_e.next();
        iter_e.remove();
        assertFalse(iter_e.hasNext());
        assertNull(iter_e.next());
    }

    private String[] getStrings()
    {
        String[] strings = new String[1000];
        for (int i = 0; i < 1000; i++)
        {
            String string = "String" + i;
            strings[i] = string;
        }
        return strings;
    }
}