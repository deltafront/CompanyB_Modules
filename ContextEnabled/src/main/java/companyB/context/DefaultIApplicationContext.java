package companyB.context;

import companyB.common.utils.FactoryUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
            Class c = container.get_Class();
            String id = container.getId();
            Object[] args = container.getArgs();
            Validate.notNull(c,"Container is null.");
            Validate.notBlank(id,"Id required.");
            Object instance = factoryUtils.getInstance(c, args);
            Validate.isTrue(this.associate(id, instance));
            LOGGER.trace(String.format("Instantiated instance of '%s' (number of args: %d).",
                    c.getCanonicalName(), args.length));
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public <Value> boolean associate(String key, Value value)
    {
        boolean inserted = false;
        if (!mapping.containsKey(key))
        {
            mapping.put(key, value);
            inserted = true;
            LOGGER.trace(String.format("Associating key %s with value %s ? %b.", key, String.valueOf(value), inserted));
        }
        return inserted;
    }

    @Override
    public <Value> Value get(String key)
    {
        Value value = (Value) mapping.get(key);
        LOGGER.trace(String.format("Returning value for key '%s' : [%s].", key, String.valueOf(value)));
        return value;
    }

    @Override
    public Set<String> getKeys()
    {
        LOGGER.trace(String.format("Returning %s keys.", mapping.keySet().size()));
        return mapping.keySet();
    }

    @Override
    public <T> T getInstance(Class<T> c, Object[] args, String id)
    {
        if (!mapping.containsKey(id)) mapping.put(id, factoryUtils.getInstance(c, args));
        T out = (T) mapping.get(id);
        LOGGER.trace(String.format("Instance of %s returned? %b", c.getCanonicalName(), null != out));
        return out;
    }

    @Override
    public void clear()
    {
        mapping.clear();
    }
}
