package companyB.common.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Splits Collection into a List of Lists.
 * @author C.A. Burrell (deltafront@gmail.com)
 */
public class CollectionsSplitter extends UtilityBase
{
    public enum optimization_strategy
    {
        number_of_lists(CollectionsSplitter::number_of_lists),
        number_of_items(CollectionsSplitter::number_of_items);
        optimization_strategy(BiFunction<Collection,Integer, List<List>> function)
        {
            this.biFunction = function;
        }
        BiFunction<Collection, Integer, List<List>>biFunction;
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
     */
    @SuppressWarnings("unchecked")
    public List<List> split(Collection collection, int split_num, optimization_strategy strategy)
    {
        final int num = (null != collection) ?
                (split_num == 0 || split_num > collection.size())
                ? collection.size() : split_num : -1;
        return  (null != collection) ?
                strategy.biFunction.apply(collection,num) :
                new LinkedList<>();
    }

    @SuppressWarnings({"unchecked", "WhileLoopReplaceableByForEach"})
    private static List<List> number_of_lists(Collection collection, int num)
    {
        final List<List> list = new LinkedList<>();
        IntStream.range(0,num).forEach(i->list.add(new LinkedList()));
        final AtomicInteger count = new AtomicInteger(0);
        collection.forEach((next)-> generateOuterLists(num, list, count, next));
        return list;
    }

    @SuppressWarnings("unchecked")
    private static void generateOuterLists(int num, List<List> list, AtomicInteger count, Object next)
    {
        if (count.get() == num)count.getAndSet(0);
        list.get(count.get()).add(next);
        count.getAndIncrement();
    }

    @SuppressWarnings("unchecked")
    private static List<List> number_of_items(Collection collection, int num)
    {
        List<List> list = new LinkedList<>();
        final AtomicInteger count = new AtomicInteger(0);
        final List _list = new LinkedList();
        collection.forEach((next) -> generateOuterList(num, list, count, _list, next));
        if (_list.size() > 0) list.add(_list);
        return list;
    }

    @SuppressWarnings("unchecked")
    private static void generateOuterList(int num, List<List> list, AtomicInteger count, List _list, Object next)
    {
        _list.add(next);
        count.incrementAndGet();
        if (count.get() == num) addInnerList(list, count, _list);
    }

    @SuppressWarnings("unchecked")
    private static void addInnerList(List<List> list, AtomicInteger count, List _list)
    {
        count.getAndSet(0);
        final List inner = new LinkedList<>();
        inner.addAll(_list);
        list.add(inner);
        _list.clear();
    }
}
