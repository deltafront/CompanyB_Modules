package companyB.http.site.context;


import com.google.gson.Gson;
import companyB.http.site.Site;
import org.apache.commons.lang3.Validate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Container that holds applicable data concerning the current context.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class Context
{
    private final String pageId;
    private final String operation;
    private final Timestamp operationStart;
    private Timestamp operationEnd;
    private final Site site;
    private final String contextAttributeName;
    private boolean operationEnded;
    private final Map<String,Object> objects;

    /**
     * Default constructor.
     * @param pageId Unique Id for page.
     * @param operation Operation that is being performed.
     * @param site Site object that contains data concerning the site as a whole.
     * @param contextAttributeName Name that this context is to be keyed to when it is wrapped in an HttpSession.
     */
    public Context(final String pageId, final String operation, final Site site, final String contextAttributeName)
    {
        Validate.notBlank(pageId);
        Validate.notBlank(operation);
        Validate.notNull(site);
        Validate.notBlank(contextAttributeName);
        this.contextAttributeName = contextAttributeName;
        this.site = site;
        this.pageId = pageId;
        this.operation = operation;
        this.operationStart = new Timestamp(System.currentTimeMillis());
        this.objects = new HashMap<>();
    }

    /**
     * Ends the current operation. This is a one-time process, meaning that for any given context its' operation can
     * only be ended once.
     * @return Timestamp of when current operation has been ended.
     * @since 1.0
     */
    public Timestamp endOp()
    {
        if(!operationEnded)
        {
            this.operationEnd = new Timestamp(System.currentTimeMillis());
            operationEnded = true;
        }
        return operationEnd;
    }

    /**
     * @return pageId.
     * @since 1.0
     */
    public String getPageId()
    {
        return pageId;
    }
    /**
     * @return Current operation.
     * @since 1.0
     */
    public String getOperation()
    {
        return operation;
    }
    /**
     * @return Start time of current operation.
     * @since 1.0
     */
    public Timestamp getOperationStart()
    {
        return operationStart;
    }
    /**
     * @return End time of current operation.
     * @since 1.0
     */
    public Timestamp getOperationEnd()
    {
        return operationEnd;
    }
    /**
     * @return Duration of operation covered by context.
     * @since 1.0
     */
    public Long getDuration()
    {
        return operationEnd.getTime() - operationStart.getTime();
    }

    /**
     * @return Site attached to context.
     * @since 1.0
     */
    public Site getSite()
    {
        return site;
    }

    /**
     * @return Name that this context is keyed to in the session.
     * @since 1.0
     */
    public String getContextAttributeName()
    {
        return contextAttributeName;
    }

    /**
     * Associates an object reference with this context.
     * @param key Key to associate object reference with.
     * @param object Object to be associated.
     * @since 1.0
     */
    public void setObject(String key, Object object)
    {
        Validate.notBlank(key);
        objects.put(key,object);
    }

    /**
     *
     * @param key Key that is associated with reference.
     * @param <T> Type parameter.
     * @return Object associated with reference.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public <T>T get(String key)
    {
        return (T)objects.get(key);
    }

    /**
     * @return Listing of all keys in mapping.
     * @since 1.0
     */
    public Set<String>getReferences()
    {
        return objects.keySet();
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
