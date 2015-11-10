package companyB.common.cache;

import companyB.common.cache.utils.NullStringValueNormalizer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base classes for external caches.
 * @author Charles A. Burrell (deltafront@gmail.com)
 * @since 2.1.0
 */
public abstract class AbstractExternalCache
{
    protected final static Logger LOGGER = LoggerFactory.getLogger(AbstractExternalCache.class);
    protected final NullStringValueNormalizer normalizer;
    protected final String name;

    protected AbstractExternalCache(String name)
    {
        Validate.notBlank(name,"Cache name is required.");
        this.name  = name;
        this.normalizer = new NullStringValueNormalizer();
    }
}
