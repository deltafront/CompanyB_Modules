package companyB.common.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Utilities. Main purpose is to provide a common logging point.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public abstract class UtilityBase
{
    protected final static Logger LOGGER = LoggerFactory.getLogger(UtilityBase.class);
    protected final Gson GSON = new Gson();
}
