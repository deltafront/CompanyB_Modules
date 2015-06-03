package companyB.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Splits Collection into a List of Lists.
 *
 * @author C.A. Burrell (deltafront@gmail.com)
 * @since  1.0.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public class CollectionsSplitter extends UtilityBase
{
    public enum optimization_strategy
    {
        number_of_lists, number_of_items
    }

    /**
     * Returns a list of lists, wherein all contained lists have all of the items in the collection
     *
     * @param collection Collection to be split
     * @param split_num  If you choose optimization_strategy.number_of_lists, then this will be the number of individual lists returned, with X number of items in each list
     *                   for a total of all of the items in the collection.
     *                   If you choose optimization_strategy.number_if_items, then a List containing X number of lists will be returned, with each list
     *                   containing <strong>at most</strong> split_num items.
     * @param strategy   one of optimization_strategy.number_of_lists | optimization_strategy.number_of_items
     * @return list of lists.
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public List<List> split(Collection collection, int split_num, optimization_strategy strategy)
    {
        if(null != collection) LOGGER.trace(String.format("Size of collection:\t%d\nSplit Number:\t%d\nStrategy:\t%s",
                collection.size(), split_num,strategy.name()));
        if (null == collection)return new LinkedList<>();
        int num = (split_num == 0 || split_num > collection.size())
                ? collection.size() : split_num;
        List<List>lists =  (strategy == optimization_strategy.number_of_items)
                ? number_of_items(collection, num) : number_of_lists(collection, num);
        LOGGER.trace(String.format("Returning new list of %d elements.",lists.size()));
        return lists;
    }

    @SuppressWarnings({"unchecked", "WhileLoopReplaceableByForEach"})
    private List<List> number_of_lists(Collection collection, int num)
    {
        List<List> list = new LinkedList<>();
        for (int i = 0; i < num; i++)list.add(new LinkedList());
        int count = 0;
        Iterator iter = collection.iterator();
        while (iter.hasNext())
        {
            if (count == num)count = 0;
            list.get(count).add(iter.next());
            count++;
        }
        LOGGER.trace(String.format("Returning a master list that contains %d lists.",list.size()));
        return list;
    }

    //helper methods
    @SuppressWarnings("unchecked")
    private List<List> number_of_items(Collection collection, int num)
    {
        List<List> list = new LinkedList<>();
        Iterator iter = collection.iterator();
        int count = 0;
        List _list = new LinkedList();
        while (iter.hasNext())
        {
            _list.add(iter.next());
            count++;
            if (count == num)
            {
                count = 0;
                list.add(_list);
                _list = new LinkedList();
            }
        }
        if (_list.size() > 0) list.add(_list);
        LOGGER.trace(String.format("Returning a master list of %d lists in which each list contains at least %d elements.",
                list.size(),num));
        return list;
    }
}
