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
        Validate.notBlank(userId,"UserId is required.");
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
        Validate.notNull(context,"Context is required to add activity to.");

        UserActivity activity = new UserActivity(context);
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
        private final String pageId;
        private final Timestamp visited;
        private final String operation;
        private final Long duration;

        /**
         * Default constructor.
         * @param pageId  - ID of the page.
         * @param visited When page was visited.
         * @param operation Operation performed / in progress.
         * @param duration Duration of stay on the page.
         * @since 1.1.1
         */
        public UserActivity(String pageId, Timestamp visited, String operation, Long duration)
        {
            this.pageId = pageId;
            this.visited = visited;
            this.operation = operation;
            this.duration = duration;
        }

        public UserActivity(Context context)
        {
            this.duration = context.getOperationStart().getTime() - context.getOperationEnd().getTime();
            this.pageId = context.getPageId();
            this.operation = context.getOperation();
            this.visited = context.getOperationStart();
        }

        /**
         * @return ID of the page.
         * @since 1.1.1
         */
        public String getPageId()
        {
            return pageId;
        }

        /**
         * @return When page was visited.
         * @since 1.1.1
         */
        public Timestamp getVisited()
        {
            return visited;
        }

        /**
         * @return Operation performed / in progress.
         * @since 1.1.1
         */
        public String getOperation()
        {
            return operation;
        }

        /**
         * @return Duration of stay on the page.
         * @since 1.1.1
         */
        public Long getDuration()
        {
            return duration;
        }
    }
}
