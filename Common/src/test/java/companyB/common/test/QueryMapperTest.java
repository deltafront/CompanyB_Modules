package companyB.common.test;

import companyB.common.utils.QueryMapper;
import junit.framework.TestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@Test(groups = {"unit","common","utils","query.mapper"})
public class QueryMapperTest
{
    private String requestQuery;
    private QueryMapper queryMapper;

    @BeforeMethod
    public void before()
    {
        queryMapper = new QueryMapper();
    }

    public void oneParam()
    {

        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        Map<String, List<String>> mapping = getStringListMap();
        verifyList(mapping,"one",1);
    }

    public void twoParams()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        Map<String, List<String>> mapping = getStringListMap();
        verifyList(mapping,"two",2);
    }

    public void threeParams()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        Map<String, List<String>> mapping = getStringListMap();
        verifyList(mapping,"three",3);
    }

    public void oneParamNoValue()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        Map<String, List<String>> mapping = getStringListMap();
        List<String>test  = mapping.get("test");
        assertNotNull(test);
        assertEquals(1,test.size());
        assertNull(test.get(0));
    }

    public void invalidParam()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        Map<String,List<String>>mapping = getStringListMap();
        List<String>zero  = mapping.get("zero");
        assertNull(zero);
    }

    private Map<String, List<String>> getStringListMap()
    {
        Map<String,List<String>>mapping = queryMapper.mapRequestQuery(requestQuery);
        assertNotNull(mapping);
        return mapping;
    }

    private void verifyList(Map<String,List<String>> mapping, String key, int num_expected)
    {
        assertNotNull(mapping);
        List<String>listings = mapping.get(key);
        if(0 == num_expected)assertNull(listings);
        else
        {
            assertNotNull(listings);
            assertEquals(num_expected, listings.size());
            for(int index = 0; index<listings.size(); index++)
            {
                String listing = listings.get(index);
                TestCase.assertEquals(String.format("%s.%d",key,index),listing);
            }
        }
    }
}