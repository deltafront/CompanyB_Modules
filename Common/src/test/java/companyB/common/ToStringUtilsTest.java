package companyB.common;

import companyB.common.utils.ToStringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ToStringUtilsTest
{
    private String basicList = "[0,1,2,3,4,5,6,7,8,9]";
    private String listOfList = "[[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4],[0,1,2,3,4]]";
    private String listOfMap = "[{this:that},{this:that},{this:that},{this:that},{this:that}]";

    private String basicMap = "{0:0,1:1,2:2,3:3,4:4}";
    private String mapOfList = "{0:[],1:[0],2:[0,1],3:[0,1,2],4:[0,1,2,3]}";
    private String mapOfMap = "{0:{},1:{0:0},2:{0:0,1:1},3:{0:0,1:1,2:2},4:{0:0,1:1,2:2,3:3}}";

    @Test
    public void iterableToStringBasic()
    {
        List<Integer> iterable = new LinkedList();
        for (int i = 0; i < 10; i++)
        {
            iterable.add(i);
        }
        String out = ToStringUtils.iterableToString(iterable);
        assertNotNull(out);
        assertEquals(basicList,out);
    }
    @Test
    public void iterableToStringList()
    {
        List<List<Integer>> iterable = new LinkedList();
        for (int i = 0; i < 5; i++)
        {
            List<Integer>inner = new LinkedList<>();
            for(int j=0; j < 5; j++)
            {
                inner.add(j);
            }
            iterable.add(inner);
        }
        String out = ToStringUtils.iterableToString(iterable);
        assertNotNull(out);
        assertEquals(listOfList,out);
    }
    @Test
    public void iterableToStringMap()
    {
        List<Map<String,String>> iterable = new LinkedList();
        for (int i = 0; i < 5; i++)
        {
            Map<String,String> map = new HashMap<>();
            map.put("this","that");
            iterable.add(map);
        }
        String out = ToStringUtils.iterableToString(iterable);
        assertNotNull(out);
        assertEquals(listOfMap,out);
    }

    @Test
    public void mapToStringBasic()
    {
        Map<Integer,Integer>map = new HashMap<>();
        for(int i= 0; i < 5; i++)
        {
            map.put(i,i);
        }
        String out = ToStringUtils.mapToString(map);
        assertNotNull(out);
        assertEquals(basicMap,out);
    }
    @Test
    public void mapToStringList()
    {
        Map<Integer,List<Integer>>map = new HashMap<>();
        for(int i= 0; i < 5; i++)
        {
            List<Integer>list = new LinkedList<>();
            for(int j=0; j < i; j++)
            {
                list.add(j);
            }
            map.put(i,list);
        }
        String out = ToStringUtils.mapToString(map);
        assertNotNull(out);
        assertEquals(mapOfList,out);
    }
    @Test
    public void mapToStringMap()
    {
        Map<Integer,Map<Integer,Integer>>map = new HashMap<>();
        for(int i= 0; i < 5; i++)
        {
            Map<Integer,Integer>inner = new HashMap<>();
            for(int j=0; j < i; j++)
            {
                inner.put(j,j);
            }
            map.put(i,inner);
        }
        String out = ToStringUtils.mapToString(map);
        assertNotNull(out);
        assertEquals(mapOfMap,out);
    }
}