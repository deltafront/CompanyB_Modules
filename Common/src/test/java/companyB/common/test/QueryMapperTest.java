package companyB.common.test;

import companyB.common.utils.QueryMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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
        verifyList("one",1);
    }

    public void twoParams()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        verifyList("two",2);
    }

    public void threeParams()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        verifyList("three",3);
    }

    public void oneParamNoValue()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        final Map<String, List<String>> mapping = getStringListMap();
        final List<String>test  = mapping.get("test");
        assertThat(test,is(not(nullValue())));
        assertThat(test.size(),is(equalTo(1)));
        assertThat(test.get(0),is(nullValue()));
    }

    public void invalidParam()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        final Map<String,List<String>>mapping = getStringListMap();
        final List<String>zero  = mapping.get("zero");
        assertThat(zero,is(nullValue()));
    }

    private Map<String, List<String>> getStringListMap()
    {
        final Map<String,List<String>>mapping = queryMapper.mapRequestQuery(requestQuery);
        assertThat(mapping,is(not(equalTo(nullValue()))));
        return mapping;
    }

    private void verifyList(String key, int num_expected)
    {
        final Map<String,List<String>>mapping = getStringListMap();
        assertThat(mapping,is(not(nullValue())));
        final List<String>listings = mapping.get(key);
        if(0 == num_expected)assertThat(listings,is(equalTo(nullValue())));
        else
        {
            assertThat(listings,is(not(nullValue())));
            assertThat(listings.size(),is(equalTo(num_expected)));
            IntStream.range(0,listings.size()).forEach((index)->
            {
                final String listing = listings.get(index);
                assertThat(String.format("%s.%d",key,index),is(equalTo(listing)));
            });
        }
    }
}