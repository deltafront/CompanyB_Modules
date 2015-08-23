package companyB.cache;

import companyB.cache.utils.NullStringValueNormalizer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chburrell on 5/5/15.
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
