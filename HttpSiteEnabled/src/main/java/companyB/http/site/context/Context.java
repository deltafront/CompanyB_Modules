package companyB.http.site.context;


import com.google.gson.Gson;
import companyB.http.site.Site;
import org.apache.commons.lang3.Validate;

import java.sql.Timestamp;

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

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
