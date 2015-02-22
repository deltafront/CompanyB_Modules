package companyB.common;

import companyB.common.utils.CollectionsSplitter;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")

public class ListSplitterTest
{
    @Test
    public void NullSet()
    {
        Set set = null;
        assertEquals(0, CollectionsSplitter.split(set, 0, CollectionsSplitter.optimization_strategy.number_of_lists).size());
    }

    @Test
    public void TwoItemsSet()
    {
        Set<String> set = new LinkedHashSet<String>();
        set.add("one");
        set.add("two");
        List<List> lists = CollectionsSplitter.split(set, 2, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertEquals(2, lists.size());
        for (List list : lists)
        {
            assertEquals(1, list.size());
        }
    }

    @Test
    public void SplitNumSet()
    {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < 1000; i++)
        {
            set.add("Object" + i);
        }
        List<List> lists = CollectionsSplitter.split(set, 42, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertEquals(42, lists.size());
        int size = (1000 / 42);
        String message = "Expected list size of " + (size - 1) + "' or " + size + "' or '" + (size + 1) + "'. Got '";
        for (List _list : lists)
        {
            assertTrue(message + _list.size() + "'", _list.size() == size || _list.size() == size + 1 || _list.size() == size - 1);
        }
    }

    @Test
    public void EvenNumSet()
    {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < 84; i++)
        {
            set.add("Object" + i);
        }
        List<List> lists = CollectionsSplitter.split(set, 42, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertEquals(42, lists.size());
        for (List list : lists)
        {
            assertEquals(2, list.size());
        }
    }

    @Test
    public void ItemsPerListManyLists()
    {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < 1002; i++)
        {
            set.add("Object" + i);
        }
        List<List> lists = CollectionsSplitter.split(set, 10, CollectionsSplitter.optimization_strategy.number_of_items);
        assertEquals(101, lists.size());
        for (int i = 0; i < lists.size(); i++)
        {
            List list = lists.get(i);
            if (i != (lists.size() - 1))
            {
                assertEquals(10, list.size());
            }
            else
            {
                assertEquals(2, list.size());
            }
        }
    }

    @Test
    public void ItemsPerListOneList()
    {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < 1002; i++)
        {
            set.add("Object" + i);
        }
        List<List> lists = CollectionsSplitter.split(set, 1002, CollectionsSplitter.optimization_strategy.number_of_items);
        assertEquals(1, lists.size());
        for (int i = 0; i < lists.size(); i++)
        {
            List list = lists.get(i);
            assertEquals(1002, list.size());
        }
    }

    @Test
    public void getOptimizationValues()
    {
        for (CollectionsSplitter.optimization_strategy strategy : CollectionsSplitter.optimization_strategy.values())
        {
            assertNotNull(CollectionsSplitter.optimization_strategy.valueOf(strategy.name()));
        }
    }
}