package companyB.http.session;

import java.util.List;

/**
 * Container for Default Session Attributes.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class DefaultSessionAttributes
{
    /**
     * All of the attribute names that this session is supposed to contain.
     */
    public List<String>defaultSessionAttributeNames;
    /**
     * Max interval for this session.
     */
    public Integer maxInterval = -1;

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder(String.format("Max interval: %s",maxInterval));
        stringBuilder.append("\nAttribute names:");
        if(null != defaultSessionAttributeNames)
        {
            for(String name : defaultSessionAttributeNames)
            {
                stringBuilder.append(String.format("\n\t%s",name));
            }
        }
        stringBuilder.trimToSize();
        return stringBuilder.toString();
    }
}
