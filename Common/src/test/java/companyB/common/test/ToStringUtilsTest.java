package companyB.common.test;

import companyB.common.utils.ToStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Test(groups = {"unit","to.string.utils","common"})
public class ToStringUtilsTest
{
    private ToStringUtils toStringUtils;

    @BeforeMethod
    public void before()
    {
        toStringUtils = new ToStringUtils();
    }
    public void iterableToStringBasic()
    {
        String actual = toStringUtils.iterableToString(getIntegers(10));
        String expected = "[0,1,2,3,4,5,6,7,8,9]";
        doCompare(actual,expected);
    }

    public void iterableToStringList()
    {
        String actual = toStringUtils.iterableToString(getLists());
        String expected = "[[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4]]";
        doCompare(actual,expected);
    }

    public void iterableToStringMap()
    {
        String actual = toStringUtils.iterableToString(getMaps());
        String expected = "[{this:that},{this:that},{this:that},{this:that},{this:that}]";
        doCompare(actual,expected);
    }

    public void mapToStringBasic()
    {
        String actual = toStringUtils.mapToString(getIntegerIntegerMap());
        String expected = "{0:0,1:1,2:2,3:3,4:4}";
        doCompare(actual,expected);
    }

    public void mapToStringList()
    {
        String actual = toStringUtils.mapToString(getIntegerListMap());
        String expected = "{0:[],1:[0],2:[0,1],3:[0,1,2],4:[0,1,2,3]}";
        doCompare(actual,expected);
    }

    public void mapToStringMap()
    {
        String actual = toStringUtils.mapToString(getIntegerMapMap());
        String expected = "{0:{},1:{0:0},2:{0:0,1:1},3:{0:0,1:1,2:2},4:{0:0,1:1,2:2,3:3}}";
        doCompare(actual,expected);
    }

    private void doCompare(String actual, String expected)
    {
        assertThat(actual,is(not(nullValue())));
        assertThat(actual,is(equalTo(expected)));
    }
    private Map<Integer, Map<Integer, Integer>> getIntegerMapMap()
    {
        final Map<Integer,Map<Integer,Integer>>map = new HashMap<>();
        IntStream.range(0,5).forEach((i)->
        {
            final Map<Integer,Integer>inner = new HashMap<>();
            IntStream.range(0,i).forEach((j)->inner.put(j,j));
            map.put(i,inner);
        });
        return map;
    }

    private List<Integer> getIntegers(int num)
    {
        List<Integer> iterable = new LinkedList<>();
        IntStream.range(0,num).forEach(iterable::add);
        return iterable;
    }
    private List<List<Integer>> getLists()
    {
        List<List<Integer>> iterable = new LinkedList<>();
        IntStream.range(0,5).forEach((index)->iterable.add(getIntegers(5)));
        return iterable;
    }
    private List<Map<String, String>> getMaps()
    {
        List<Map<String,String>> iterable = new LinkedList<>();
        IntStream.range(0,5).forEach((index)->
        {
            Map<String,String> map = new HashMap<>();
            map.put("this","that");
            iterable.add(map);
        });
        return iterable;
    }
    private Map<Integer, Integer> getIntegerIntegerMap()
    {
        Map<Integer,Integer>map = new HashMap<>();
        IntStream.range(0,5).forEach((index)-> map.put(index,index));
        return map;
    }
    private Map<Integer, List<Integer>> getIntegerListMap()
    {
        Map<Integer,List<Integer>>map = new HashMap<>();
        IntStream.range(0,5).forEach((i)->
        {
            List<Integer>list = new LinkedList<>();
            IntStream.range(0,i).forEach(list::add);
            map.put(i,list);
        });
        return map;
    }
}
