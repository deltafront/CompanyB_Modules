package companyB.http.session;

import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Container for Default Session Attributes.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 1.0.0
 */
public class DefaultSessionAttributes
{
    private final List<String>defaultSessionAttributeNames;

    private Integer maxInterval = -1;

    public DefaultSessionAttributes(List<String> defaultSessionAttributeNames)
    {
        Validate.notNull(defaultSessionAttributeNames);
        this.defaultSessionAttributeNames = defaultSessionAttributeNames;
    }

    /**
     * Sets the max interval time for this session.
     * @param maxInterval Max interval time.
     * @return This instance of DefaultSessionAttributes with the new max interval.
     * @since 1.1.0
     */
    public DefaultSessionAttributes withMaxInterval(Integer maxInterval)
    {
        Validate.notNull(maxInterval);
        this.maxInterval = maxInterval;
        return this;
    }
    /**
     * @return All of the attribute names that this session is supposed to contain.
     * @since 1.1.0
     */
    public List<String> getDefaultSessionAttributeNames()
    {
        return defaultSessionAttributeNames;
    }

    /**
     * @return Max interval for this session.
     * @since 1.1.0
     */
    public Integer getMaxInterval()
    {
        return maxInterval;
    }

    /**
     * @return Max interval for this session.
     * @since 1.1.0
     */
    public String toString()
    {
        String out= String.format("Max interval: %s",maxInterval);
        out += "\nAttribute names:";
        if(null != defaultSessionAttributeNames)
            for(String name : defaultSessionAttributeNames)
                out += String.format("\n\t%s",name);
        return out;
    }

}
