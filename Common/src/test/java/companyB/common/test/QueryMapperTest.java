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
public class QueryMapperTest extends TestBase
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
        validateNotNull(test);
        validateEquality(test.size(),1);
        validateNull(test.get(0));
    }

    public void invalidParam()
    {
        requestQuery = "?one=one.0&two=two.0&two=two.1&&three=three.0&three=three.1&&&three=three.2&test";
        final Map<String,List<String>>mapping = getStringListMap();
        final List<String>zero  = mapping.get("zero");
        validateNull(zero);
    }

    private Map<String, List<String>> getStringListMap()
    {
        final Map<String,List<String>>mapping = queryMapper.mapRequestQuery(requestQuery);
        validateNotNull(mapping);
        return mapping;
    }

    private void verifyList(String key, int num_expected)
    {
        final Map<String,List<String>>mapping = getStringListMap();
        validateNotNull(mapping);
        final List<String>listings = mapping.get(key);
        if(0 == num_expected)assertThat(listings,is(equalTo(nullValue())));
        else
        {
            validateNotNull(listings);
            validateEquality(listings.size(),num_expected);
            IntStream.range(0,listings.size()).forEach((index)->
            {
                final String listing = listings.get(index);
                validateEquality(String.format("%s.%d",key,index),listing);
            });
        }
    }
}