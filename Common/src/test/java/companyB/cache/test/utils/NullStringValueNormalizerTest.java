package companyB.cache.test.utils;

import companyB.common.cache.utils.NullStringValueNormalizer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

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
        assertEquals(NullStringValueNormalizer.NULL_STRING,normalizer.cleanNullStringValue(null));
    }
    public void normalizerFromBlank()
    {
        assertEquals(NullStringValueNormalizer.NULL_STRING,normalizer.cleanNullStringValue(""));
    }
    public void normalizeFromNotNull()
    {
        assertEquals("foo",normalizer.cleanNullStringValue("foo"));
    }

    public void normalizeToNull()
    {
        assertNull(normalizer.dirtyNullStringValue(NullStringValueNormalizer.NULL_STRING));
    }
    public void normalizeToNotNull()
    {
        String value = normalizer.dirtyNullStringValue("foo");
        assertNotNull(value);
        assertEquals("foo",value);
    }

}
