package companyB.cache.test.utils;

import companyB.cache.utils.NullStringValueNormalizer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Test(groups = {"unit","http.cache.enabled","utils","null.string.value.normalizer"})
public class NullStringValueNormalizerTest
{
    private NullStringValueNormalizer normalizer;

    @BeforeMethod
    public void before()
    {
        normalizer = new NullStringValueNormalizer();
    }

    public void normalizerFromNull()
    {
        assertThat(NullStringValueNormalizer.NULL_STRING,is(equalTo(normalizer.cleanNullStringValue(null))));
    }
    public void normalizerFromBlank()
    {
        assertThat(NullStringValueNormalizer.NULL_STRING,is(equalTo(normalizer.cleanNullStringValue(""))));
    }
    public void normalizeFromNotNull()
    {
        assertThat("foo",is(equalTo(normalizer.cleanNullStringValue("foo"))));
    }

    public void normalizeToNull()
    {
        assertThat(normalizer.dirtyNullStringValue(NullStringValueNormalizer.NULL_STRING),is(nullValue()));
    }
    public void normalizeToNotNull()
    {
        String value = normalizer.dirtyNullStringValue("foo");
        assertThat(value,is(not(nullValue())));
        assertThat("foo",is(equalTo(value)));
    }

}
