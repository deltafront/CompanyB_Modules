package companyB.common;

import companyB.common.constants.DateConstants;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

@Test(groups = {"unit","iso8601","common"})
public class Iso8601Test
{
    public void testIso8601()
    {
        //"yyyy-MM-dd'T'HH:mm:ssX"
        String dateString = "1969-08-03T15:15:00-07:00";
        try
        {
            Date date = DateConstants.ISO_8601.parse(dateString);
            assertNotNull(date);
            Calendar calendar = Calendar.getInstance();
            assertNotNull(calendar);
            calendar.setTimeInMillis(date.getTime());
            assertEquals(1969,calendar.get(Calendar.YEAR));
            assertEquals(Calendar.AUGUST,calendar.get(Calendar.MONTH));
            assertEquals(3,calendar.get(Calendar.DAY_OF_MONTH));
            assertEquals(15,calendar.get(Calendar.HOUR_OF_DAY));
            assertEquals(15,calendar.get(Calendar.MINUTE));
            assertEquals(0,calendar.get(Calendar.SECOND));
            TimeZone timeZone = calendar.getTimeZone();
            assertNotNull(timeZone);
            assertEquals("Pacific Standard Time",timeZone.getDisplayName());

        }
        catch (ParseException e)
        {
            e.printStackTrace();
            fail("Unexpected exception thrown.");
        }
    }
    public void testFromString()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR,1969);
        calendar.set(Calendar.MONTH,Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH,3);
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,15);
        calendar.set(Calendar.SECOND,0);
        Date date = new Date(calendar.getTimeInMillis());
        String dateString = DateConstants.ISO_8601.format(date);
        assertEquals("1969-08-03T08:15:00-07",dateString);

    }
}