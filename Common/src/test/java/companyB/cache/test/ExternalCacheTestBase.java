package companyB.cache.test;

import companyB.common.cache.ExternalCache;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@SuppressWarnings({"unchecked", "BoxingBoxedValue"})
@Test
public class ExternalCacheTestBase
{
    protected ExternalCache externalCache;
    protected String name;
    protected String key;
    protected Integer max_num;

    public void before()
    {
        max_num = 1000;
        name = "test";
        key = "foo";
    }

    public void insertValue()
    {
        String value = "42";
        externalCache.insert(key, value);
        assertEquals(value, externalCache.retrieve(key));
    }
    public void replaceValue()
    {
        insertValue();
        String value = "43";
        externalCache.insert(key, value);
        assertEquals(value, externalCache.retrieve(key));
    }
    public void removeValue()
    {
        insertValue();
        String value = (String)externalCache.remove(key);
        assertEquals("42",value);
        assertNull(externalCache.retrieve(key));
    }
    public void nullInsert()
    {
        externalCache.insert(key, null);
        assertNull(externalCache.retrieve(key));
    }
}
