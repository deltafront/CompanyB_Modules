package companyB.common;

import companyB.common.utils.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;


public class DateTimeUtilsTest
{
    String date_string, long_date_string;
    Calendar cal;

    @Before
    public void setUp() throws Exception
    {
        java.util.TimeZone time_zone = java.util.TimeZone.getTimeZone("GMT");
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1969);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DATE, 3);
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 15);
        cal.set(Calendar.SECOND, 15);
        cal.setTimeZone(time_zone);
        date_string = "Aug 03 1969, 15:15:15";
        long_date_string = "Sun, 3 Aug 1969 AD. Time Zone: Greenwich Mean Time";
    }

    @After
    public void tearDown() throws Exception
    {
        cal = null;
        date_string = null;
        long_date_string = null;
    }

    @Test
    public void testGetFormattedDateCalendar()
    {
        assertEquals(DateTimeUtils.getFormattedDate(cal), date_string);
    }

    @Test
    public void testGetFormattedDateCalendarNull()
    {
        cal = null;
        assertEquals(DateTimeUtils.getFormattedDate(cal), "");
    }

    @Test
    public void testGetFormattedDateValidFormat()
    {
        String validFormat = "E, d MMM yyyy G. 'Time Zone:' zzzz";
        assertEquals(DateTimeUtils.getFormattedDate(cal, validFormat), long_date_string);
    }

    @Test
    public void testGetFormattedDateInValidFormat()
    {
        String invalidFormat = "OO:oo pp";
        assertEquals(DateTimeUtils.getFormattedDate(cal, invalidFormat), "");
    }

    @Test
    public void testGetFormattedDateZeroLengthFormat()
    {
        String invalidFormat = "";
        assertEquals(DateTimeUtils.getFormattedDate(cal, invalidFormat), "");
    }

    @Test
    public void testGetFormattedDateNullFormat()
    {
        String invalidFormat = null;
        assertEquals(DateTimeUtils.getFormattedDate(cal, invalidFormat), "");
    }
}
