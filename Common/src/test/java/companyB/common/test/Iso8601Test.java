package companyB.common.test;

import companyB.common.constants.DateConstants;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.fail;

@Test(groups = {"unit","iso8601","common","utils"})
public class Iso8601Test extends TestBase
{
    public void iso8601()
    {
        //"yyyy-MM-dd'T'HH:mm:ssX"
        String dateString = "1969-08-03T15:15:00-07:00";
        try
        {
            final Date date = DateConstants.ISO_8601.dateFormat().parse(dateString);
            validateNotNull(date);
            final Calendar calendar = Calendar.getInstance();
            validateNotNull(calendar);
            calendar.setTimeInMillis(date.getTime());
            validateEquality(1969,calendar.get(Calendar.YEAR));
            validateEquality(Calendar.AUGUST,calendar.get(Calendar.MONTH));
            validateEquality(3,calendar.get(Calendar.DAY_OF_MONTH));
            validateEquality(15,calendar.get(Calendar.HOUR_OF_DAY));
            validateEquality(15,calendar.get(Calendar.MINUTE));
            validateEquality(0,calendar.get(Calendar.SECOND));
            final TimeZone timeZone = calendar.getTimeZone();
            validateNotNull(timeZone);
            validateEquality("Pacific Standard Time",timeZone.getDisplayName());

        }
        catch (ParseException e)
        {
            fail("Unexpected exception thrown." + e.getMessage());
        }
    }
    public void fromString()
    {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR,1969);
        calendar.set(Calendar.MONTH,Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH,3);
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,15);
        calendar.set(Calendar.SECOND,0);
        final Date date = new Date(calendar.getTimeInMillis());
        final DateConstants dateConstant= DateConstants.valueOf("ISO_8601");
        final String dateString = dateConstant.dateFormat().format(date);
        validateEquality("1969-08-03T08:15:00-07",dateString);

    }
}