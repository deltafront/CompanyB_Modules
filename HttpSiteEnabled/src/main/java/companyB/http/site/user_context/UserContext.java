package companyB.http.site.user_context;

import com.google.gson.Gson;
import companyB.http.site.context.Context;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Container for activities that a user has performed on the site.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class UserContext
{
    private final String userId;
    private final List<UserActivity> userActivities;
    public final static String USER_CONTEXT_IDENTIFIER = "USER_CONTEXT";
    private final static Logger LOGGER = LoggerFactory.getLogger(UserContext.class);

    /**
     * Default constructor.
     * @param userId Unique userId
     * @since 1.0
     */
    public UserContext(final String userId)
    {
        this.userId = userId;
        this.userActivities = new LinkedList<>();
    }

    /**
     * Adds an activity to this UserContext.
     * @param context Context that contains data concerning activity to be added.
     * @since 1.0
     */
    public void addActivity(Context context)
    {
        UserActivity activity = new UserActivity();
        Validate.notNull(context);
        activity.pageId = context.getPageId();
        activity.operation = context.getOperation();
        activity.duration = context.getOperationStart().getTime() - context.getOperationEnd().getTime();
        activity.visited = context.getOperationStart();
        this.userActivities.add(activity);
        LOGGER.trace(String.format("Added User activity to userId '%s'\n%s", userId, new Gson().toJson(activity)));
    }


    /**
     * @return UserId.
     * @since 1.0
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @return All activities associated with this user.
     * @since 1.0
     */
    public List<UserActivity> getUserActivities()
    {
        return userActivities;
    }

    /**
     * Records the current user activity.
     * @author Charles Burrell (deltafront@gmail.com)
     * @version 1.0
     */
    public static class UserActivity
    {
        public String pageId;
        public Timestamp visited;
        public String operation;
        public Long duration;
    }
}
