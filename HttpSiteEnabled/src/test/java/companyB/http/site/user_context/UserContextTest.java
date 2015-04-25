package companyB.http.site.user_context;

import companyB.http.site.IsoLanguage;
import companyB.http.site.IsoLocale;
import companyB.http.site.Site;
import companyB.http.site.context.Context;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = {"unit","http.site.enabled","user.context"})
public class UserContextTest
{
    public void happyPath()
    {
        Site site = new Site("Foo","123", IsoLanguage.English, null, IsoLocale.United_States);
        Context context = new Context("index.html","login",site,"TestContext");
        context.endOp();
        UserContext userContext = new UserContext("test.user");
        userContext.addActivity(context);
        List<UserContext.UserActivity> userActivities = userContext.getUserActivities();
        AssertJUnit.assertNotNull(userActivities);
        AssertJUnit.assertEquals(1, userActivities.size());
        UserContext.UserActivity userActivity = userActivities.get(0);
        AssertJUnit.assertEquals("index.html", userActivity.getPageId());
        AssertJUnit.assertEquals("login", userActivity.getOperation());
        AssertJUnit.assertNotNull(userActivity.getDuration());
        AssertJUnit.assertNotNull(userActivity.getVisited());
    }

}