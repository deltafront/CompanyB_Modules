package companyB.common;

import companyB.common.iter_enum.NodeIterEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Enumeration;
import java.util.Iterator;

import static org.junit.Assert.*;

public class NodeIterEnumTest
{
    private Iterator<String> iter_e;
    private Enumeration<String> e_iter;
    private String test_string;
    private String fake_string;
    private NodeIterEnum<String> nie;

    @Before
    public void setUp() throws Exception
    {
        nie = new NodeIterEnum<String>();
        e_iter = (Enumeration<String>) nie;
        iter_e = (Iterator<String>) nie;
        test_string = "test string";
        fake_string = "fake string";
    }

    @After
    public void tearDown() throws Exception
    {
        iter_e = null;
        e_iter = null;
        test_string = null;
        fake_string = null;
        nie = null;
    }

    @Test
    public void testHasMoreElementsTrue()
    {
        nie.add(test_string);
        assertTrue(e_iter.hasMoreElements());
        assertEquals(test_string, e_iter.nextElement());
    }

    @Test
    public void testHasMoreElementsTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        String next = e_iter.nextElement();
        assertTrue(e_iter.hasMoreElements());
        assertEquals(test_string, next);
    }

    @Test
    public void testHasMoreElementsFalse()
    {
        assertFalse(e_iter.hasMoreElements());
        assertNull(e_iter.nextElement());
    }

    @Test
    public void testHasMoreElementsFalseIterate()
    {
        nie.add(test_string);
        e_iter.nextElement();
        assertFalse(e_iter.hasMoreElements());
    }

    @Test
    public void testNextElementTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertEquals(e_iter.nextElement(), test_string);
        assertEquals(e_iter.nextElement(), fake_string);
        assertFalse(e_iter.hasMoreElements());
    }

    @Test
    public void testNextElementFalse()
    {
        nie.add(test_string);
        assertFalse(e_iter.nextElement().equals(fake_string));
    }

    @Test
    public void testHasNextTrue()
    {
        nie.add(test_string);
        assertTrue(iter_e.hasNext());
    }

    @Test
    public void testHasNextTrueIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        assertTrue(iter_e.hasNext());
    }

    @Test
    public void testHasNextFalse()
    {
        assertFalse(iter_e.hasNext());
    }

    @Test
    public void testHasNextFalseIterate()
    {
        nie.add(test_string);
        nie.add(fake_string);
        iter_e.next();
        iter_e.next();
        assertFalse(iter_e.hasNext());
    }

    @Test
    public void testNextTrue()
    {
        nie.add(test_string);
        nie.add(fake_string);
        assertEquals(iter_e.next(), test_string);
        assertEquals(iter_e.next(), fake_string);
    }

    @Test
    public void testNextFalse()
    {
        nie.add(test_string);
        assertFalse(iter_e.next().equals(fake_string));
    }

    @Test
    public void testAdd()
    {
        nie.add(test_string);
        assertTrue(iter_e.hasNext());
    }

    @Test
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

    @Test
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
    @Test
    public void testRemove()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.next();
        iter_e.remove();
        assertFalse(iter_e.hasNext());
        assertNull(iter_e.next());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveNoInitialCallToNext()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.remove();
        fail("IllegalStateException should have been thrown.");
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveSubsequentCallToRemove()
    {
        nie.add(test_string);
        nie.add(fake_string);

        iter_e.next();
        iter_e.remove();
        iter_e.remove();
        fail("IllegalStateException should have been thrown.");
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