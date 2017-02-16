package companyB.context;

import companyB.common.utils.FactoryUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Default implementation of ApplicationContext. This implementation is meant to be thread-safe, backed by a Concurrent
 * HashMap.
 *
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class DefaultIApplicationContext implements I_ApplicationContext
{
    protected final static Logger LOGGER = LoggerFactory.getLogger(DefaultIApplicationContext.class);
    protected static Map<String, Object> mapping = Collections.synchronizedMap(new HashMap<String, Object>());
    protected final FactoryUtils factoryUtils;


    /**
     * Default constructor for class. All instances of this class share the same thread-safe mapping.
     * @since 1.0.0
     */
    public DefaultIApplicationContext()
    {
        super();
        this.factoryUtils = new FactoryUtils();
    }

    /**
     * All instances of this class share the same thread-safe mapping.
     * @param classArgsContainerList List of ClassArgsConstructors
     * @since 1.0.0
     */
    public DefaultIApplicationContext(List<ClassArgsContainer> classArgsContainerList)
    {
        this();
        for (ClassArgsContainer container : classArgsContainerList)
        {
            final Class c = container.get_Class();
            final String id = container.getId();
            final Object[] args = container.getArgs();
            Validate.notNull(c,"Container is null.");
            Validate.notBlank(id,"Id required.");
            final Object instance = factoryUtils.getInstance(c, args);
            Validate.isTrue(this.associate(id, instance));
            LOGGER.trace("Instantiated instance of '{}' (number of args: {}).",
                    c.getCanonicalName(), args.length);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public <Value> Boolean associate(String key, Value value)
    {
        final AtomicBoolean inserted = new AtomicBoolean(false);
        if (!mapping.containsKey(key))
        {
            mapping.put(key, value);
            inserted.getAndSet(true);
            LOGGER.trace("Associating key {} with value {} ? {}.", key, String.valueOf(value), inserted);
        }
        return inserted.get();
    }

    @Override
    public <Value> Value get(String key)
    {
        Value value = (Value) mapping.get(key);
        LOGGER.trace("Returning value for key '{}' : [{}].", key, String.valueOf(value));
        return value;
    }

    @Override
    public Set<String> getKeys()
    {
        LOGGER.trace("Returning {} keys.", mapping.keySet().size());
        return mapping.keySet();
    }

    @Override
    public <T> T getInstance(Class<T> c, Object[] args, String id)
    {
        if (!mapping.containsKey(id)) mapping.put(id, factoryUtils.getInstance(c, args));
        final T out = (T) mapping.get(id);
        LOGGER.trace("Instance of %s returned? {}", c.getCanonicalName(), null != out);
        return out;
    }

    @Override
    public void clear()
    {
        mapping.clear();
    }
}
