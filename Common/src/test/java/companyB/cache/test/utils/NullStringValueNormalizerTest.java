package companyB.cache.test.utils;

import companyB.cache.test.ExternalCacheTestBase;
import companyB.cache.utils.NullStringValueNormalizer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Test(groups = {"unit","http.cache.enabled","utils","null.string.value.normalizer"})
public class NullStringValueNormalizerTest extends ExternalCacheTestBase
{
    private NullStringValueNormalizer normalizer;

    @BeforeMethod
    public void before()
    {
        normalizer = new NullStringValueNormalizer();
    }

    public void normalizerFromNull()
    {
        validateEquality(NullStringValueNormalizer.NULL_STRING,normalizer.cleanNullStringValue(null));
    }
    public void normalizerFromBlank()
    {
        validateEquality(NullStringValueNormalizer.NULL_STRING,normalizer.cleanNullStringValue(""));
    }
    public void normalizeFromNotNull()
    {
        validateEquality("foo",normalizer.cleanNullStringValue("foo"));
    }

    public void normalizeToNull()
    {
        validateNull(normalizer.dirtyNullStringValue(NullStringValueNormalizer.NULL_STRING));
    }
    public void normalizeToNotNull()
    {
        String value = normalizer.dirtyNullStringValue("foo");
        assertThat(value,is(not(nullValue())));
        assertThat("foo",is(equalTo(value)));
    }

}
