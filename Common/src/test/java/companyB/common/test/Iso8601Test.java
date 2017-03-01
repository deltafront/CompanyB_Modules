package companyB.common.test;

import companyB.common.constants.DateConstants;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Test(groups = {"unit","iso8601","common","utils"})
public class Iso8601Test
{
    public void iso8601()
    {
        //"yyyy-MM-dd'T'HH:mm:ssX"
        String dateString = "1969-08-03T15:15:00-07:00";
        try
        {
            final Date date = DateConstants.ISO_8601.dateFormat().parse(dateString);
            assertNotNull(date);
            final Calendar calendar = Calendar.getInstance();
            assertNotNull(calendar);
            calendar.setTimeInMillis(date.getTime());
            assertThat(1969,is(equalTo(calendar.get(Calendar.YEAR))));
            assertThat(Calendar.AUGUST,is(equalTo(calendar.get(Calendar.MONTH))));
            assertThat(3,is(equalTo(calendar.get(Calendar.DAY_OF_MONTH))));
            assertThat(15,is(equalTo(calendar.get(Calendar.HOUR_OF_DAY))));
            assertThat(15,is(equalTo(calendar.get(Calendar.MINUTE))));
            assertThat(0,is(equalTo(calendar.get(Calendar.SECOND))));
            final TimeZone timeZone = calendar.getTimeZone();
            assertThat(timeZone,is(not(nullValue())));
            assertThat("Pacific Standard Time",is(equalTo(timeZone.getDisplayName())));

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
        assertThat("1969-08-03T08:15:00-07",is(equalTo(dateString)));

    }
}