package companyB.cache.impl.memory;

import companyB.cache.AbstractExternalCache;
import companyB.cache.ExternalCache;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Memory-based implementation of ExternalCache. This cache is backed by an internal hash map.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class MemoryExternalCache extends AbstractExternalCache implements ExternalCache<String,String>
{
    private final Map<String,String> cache;

    /**
     * Using this constructor will set the initial load to the highest Integer value permissible.
     * @param name Name of the cache. Required.
     */
    public MemoryExternalCache(String name)
    {
        this(name,1000*1000);
    }

    /**
     * @param name Name of the cache. Required.
     * @param initialLoad - Initial load of the cache. Required.
     */
    public MemoryExternalCache(String name, Integer initialLoad)
    {
        super(name);
        Validate.notBlank(name,"Cache name must be supplied.");
        Validate.notNull(initialLoad,"Initial load must be supplied.");
        Validate.isTrue(initialLoad > 0,"Initial load must be greater than zero.");
        cache = Collections.synchronizedMap(new HashMap<>(initialLoad));
    }


    @SuppressWarnings("unchecked")
    @Override
    public void insert(String key, String value)
    {
        Validate.notBlank(key,"Non-blank key must be provided.");
        cache.put(key,value);

    }

    @Override
    public String retrieve(String key)
    {
        Validate.notBlank(key,"Non-blank key must be provided.");
        return cache.get(key);
    }

    @Override
    public void clear()
    {
        cache.clear();
    }

    @Override
    public String remove(String key)
    {
        Validate.notBlank(key,"Non-blank key must be provided.");
        return cache.remove(key);
    }

    @Override
    public String getName()
    {
        return name;
    }
}
