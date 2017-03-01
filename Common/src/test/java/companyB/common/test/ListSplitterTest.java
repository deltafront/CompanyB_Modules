package companyB.common.test;

import companyB.common.utils.CollectionsSplitter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@SuppressWarnings("unchecked")

@Test(groups = {"unit","list.splitter","common"})
public class ListSplitterTest
{
    private CollectionsSplitter collectionsSplitter;
    @BeforeMethod
    public void before()
    {
        collectionsSplitter = new CollectionsSplitter();
    }

    
    public void nullSet()
    {
        assertThat(collectionsSplitter.split(null, 0, CollectionsSplitter.optimization_strategy.number_of_lists).size(),is(equalTo(0)));
    }

    
    public void twoItemsSet()
    {
        final Set<String> set = new LinkedHashSet<>();
        set.add("one");
        set.add("two");
        final List<List> lists = collectionsSplitter.split(set, 2, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertThat(lists.size(),is(equalTo(2)));
        lists.forEach((list)-> assertThat(list.size(),is(equalTo(1))));
    }

    
    public void splitNumSet()
    {
        final Set<String> set = new LinkedHashSet<>();
        IntStream.range(0,1000).forEach((index) -> set.add(String.format("Object %d",index)));
        final List<List> lists = collectionsSplitter.split(set, 42, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertThat(lists.size(),is(equalTo(42)));
        final int size = (1000 / 42);
        lists.forEach((list)->{
            assertThat(list.size(),is(anyOf(equalTo(size),equalTo(size +1),equalTo(size-1))));
        });

    }

    
    public void evenNumSet()
    {
        final Set<String> set = new LinkedHashSet<>();
        IntStream.range(0,84).forEach((index) -> set.add(String.format("Object %d",index)));
        final List<List> lists = collectionsSplitter.split(set, 42, CollectionsSplitter.optimization_strategy.number_of_lists);
        assertThat(lists.size(),is(equalTo(42)));
        lists.forEach((list)-> assertThat(list.size(),is(equalTo(2))));
    }

    
    public void itemsPerListManyLists()
    {
        final Set<String> set = new LinkedHashSet<>();
        IntStream.range(0,1002).forEach((index) -> set.add(String.format("Object %d",index)));
        final List<List> lists = collectionsSplitter.split(set, 10, CollectionsSplitter.optimization_strategy.number_of_items);
        assertThat(lists.size(),is(equalTo(101)));
        IntStream.range(0,lists.size()).forEach((index ->{
            final List list = lists.get(index);
            final int num = (index!=lists.size()-1) ? 10 : 2;
            assertThat(list.size(),is(equalTo(num)));
        }));
    }

    
    public void itemsPerListOneList()
    {
        final Set<String> set = new LinkedHashSet<>();
        IntStream.range(0,1002).forEach((index) -> set.add(String.format("Object %d",index)));
        final List<List> lists = collectionsSplitter.split(set, 1002, CollectionsSplitter.optimization_strategy.number_of_items);
        assertThat(lists.size(),is(equalTo(1)));
        lists.forEach((list)-> assertThat(list.size(),is(equalTo(1002))));
    }

    
    public void getOptimizationValues()
    {
        for (final CollectionsSplitter.optimization_strategy strategy : CollectionsSplitter.optimization_strategy.values())
            assertThat(CollectionsSplitter.optimization_strategy.valueOf(strategy.name()),is(not(nullValue())));

    }
}
