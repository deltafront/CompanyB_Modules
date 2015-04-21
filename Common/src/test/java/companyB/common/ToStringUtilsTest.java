package companyB.common;

import companyB.common.utils.ToStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        List<Integer> iterable = getIntegers(10);
        String out = toStringUtils.iterableToString(iterable);
        assertNotNull(out);
        String basicList = "[0,1,2,3,4,5,6,7,8,9]";
        assertEquals(basicList,out);
    }

    public void iterableToStringList()
    {
        List<List<Integer>> iterable = getLists();
        String out = toStringUtils.iterableToString(iterable);
        assertNotNull(out);
        String listOfList = "[[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4]]";
        assertEquals(listOfList,out);
    }

    public void iterableToStringMap()
    {
        List<Map<String, String>> iterable = getMaps();
        String out = toStringUtils.iterableToString(iterable);
        assertNotNull(out);
        String listOfMap = "[{this:that},{this:that},{this:that},{this:that},{this:that}]";
        assertEquals(listOfMap,out);
    }

    public void mapToStringBasic()
    {
        Map<Integer, Integer> map = getIntegerIntegerMap();
        String out = toStringUtils.mapToString(map);
        assertNotNull(out);
        String basicMap = "{0:0,1:1,2:2,3:3,4:4}";
        assertEquals(basicMap,out);
    }

    public void mapToStringList()
    {
        Map<Integer, List<Integer>> map = getIntegerListMap();
        String out = toStringUtils.mapToString(map);
        assertNotNull(out);
        String mapOfList = "{0:[],1:[0],2:[0,1],3:[0,1,2],4:[0,1,2,3]}";
        assertEquals(mapOfList,out);
    }

    public void mapToStringMap()
    {
        Map<Integer, Map<Integer, Integer>> map = getIntegerMapMap();
        String out = toStringUtils.mapToString(map);
        assertNotNull(out);
        String mapOfMap = "{0:{},1:{0:0},2:{0:0,1:1},3:{0:0,1:1,2:2},4:{0:0,1:1,2:2,3:3}}";
        assertEquals(mapOfMap,out);
    }

    private Map<Integer, Map<Integer, Integer>> getIntegerMapMap()
    {
        Map<Integer,Map<Integer,Integer>>map = new HashMap<>();
        for(int i= 0; i < 5; i++)
        {
            Map<Integer,Integer>inner = new HashMap<>();
            for(int j=0; j < i; j++)inner.put(j,j);
            map.put(i,inner);
        }
        return map;
    }

    private List<Integer> getIntegers(int num)
    {
        List<Integer> iterable = new LinkedList<>();
        for (int i = 0; i < num; i++)iterable.add(i);
        return iterable;
    }
    private List<List<Integer>> getLists()
    {
        List<List<Integer>> iterable = new LinkedList<>();
        for (int i = 0; i < 5; i++)iterable.add(getIntegers(5));
        return iterable;
    }
    private List<Map<String, String>> getMaps()
    {
        List<Map<String,String>> iterable = new LinkedList<>();
        for (int i = 0; i < 5; i++)
        {
            Map<String,String> map = new HashMap<>();
            map.put("this","that");
            iterable.add(map);
        }
        return iterable;
    }
    private Map<Integer, Integer> getIntegerIntegerMap()
    {
        Map<Integer,Integer>map = new HashMap<>();
        for(int i= 0; i < 5; i++)map.put(i,i);
        return map;
    }
    private Map<Integer, List<Integer>> getIntegerListMap()
    {
        Map<Integer,List<Integer>>map = new HashMap<>();
        for(int i= 0; i < 5; i++)
        {
            List<Integer>list = new LinkedList<>();
            for(int j=0; j < i; j++)list.add(j);
            map.put(i,list);
        }
        return map;
    }
}
