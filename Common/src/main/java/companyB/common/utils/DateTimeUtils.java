package companyB.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Simple Utility class for getting formatted datetime strings from Calendar objects.
 * @author C.A. Burrell (deltafront@gmail.com)
 * @version 1.0
 */

public class DateTimeUtils
{
    private final static Logger LOGGER  = LoggerFactory.getLogger(DateTimeUtils.class);
    /**
     * Sole overload of the getFormattedDate function that allows you to specify
     * the format of the date. The available choices are as follows (from http://java.sun.com/javase/6/docs/api/java/text/SimpleDateFormat.html):
     * <table border=0 cellspacing=3 cellpadding=0 summary="Chart shows pattern letters, date/time component, presentation, and examples.">
     * <tr bgcolor="#ccccff">
     * <th align=left>Letter
     * <th align=left>Date or Time Component
     * <th align=left>Presentation
     * <th align=left>Examples
     * <tr>
     * <td><code>G</code>
     * <td>Era designator
     * <td><a href="#text">Text</a>
     * <td><code>AD</code>
     * <p/>
     * <tr bgcolor="#eeeeff">
     * <td><code>y</code>
     * <td>Year
     * <td><a href="#year">Year</a>
     * <td><code>1996</code>; <code>96</code>
     * <tr>
     * <td><code>M</code>
     * <p/>
     * <td>Month in year
     * <td><a href="#month">Month</a>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>w</code>
     * <td>Week in year
     * <td><a href="#number">Number</a>
     * <p/>
     * <td><code>27</code>
     * <tr>
     * <td><code>W</code>
     * <td>Week in month
     * <td><a href="#number">Number</a>
     * <td><code>2</code>
     * <tr bgcolor="#eeeeff">
     * <p/>
     * <td><code>D</code>
     * <td>Day in year
     * <td><a href="#number">Number</a>
     * <td><code>189</code>
     * <tr>
     * <td><code>d</code>
     * <td>Day in month
     * <td><a href="#number">Number</a>
     * <p/>
     * <td><code>10</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>F</code>
     * <td>Day of week in month
     * <td><a href="#number">Number</a>
     * <td><code>2</code>
     * <tr>
     * <p/>
     * <td><code>E</code>
     * <td>Day in week
     * <td><a href="#text">Text</a>
     * <td><code>Tuesday</code>; <code>Tue</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>a</code>
     * <p/>
     * <td>Am/pm marker
     * <td><a href="#text">Text</a>
     * <td><code>PM</code>
     * <tr>
     * <td><code>H</code>
     * <td>Hour in day (0-23)
     * <td><a href="#number">Number</a>
     * <td><code>0</code>
     * <p/>
     * <tr bgcolor="#eeeeff">
     * <td><code>k</code>
     * <td>Hour in day (1-24)
     * <td><a href="#number">Number</a>
     * <td><code>24</code>
     * <tr>
     * <td><code>K</code>
     * <p/>
     * <td>Hour in am/pm (0-11)
     * <td><a href="#number">Number</a>
     * <td><code>0</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>h</code>
     * <td>Hour in am/pm (1-12)
     * <td><a href="#number">Number</a>
     * <td><code>12</code>
     * <p/>
     * <tr>
     * <td><code>m</code>
     * <td>Minute in hour
     * <td><a href="#number">Number</a>
     * <td><code>30</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>s</code>
     * <p/>
     * <td>Second in minute
     * <td><a href="#number">Number</a>
     * <td><code>55</code>
     * <tr>
     * <td><code>S</code>
     * <td>Millisecond
     * <td><a href="#number">Number</a>
     * <td><code>978</code>
     * <p/>
     * <tr bgcolor="#eeeeff">
     * <td><code>z</code>
     * <td>Time zone
     * <td><a href="#timezone">General time zone</a>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code>
     * <tr>
     * <p/>
     * <td><code>Z</code>
     * <td>Time zone
     * <td><a href="#rfc822timezone">RFC 822 time zone</a>
     * <td><code>-0800</code>
     * </table>
     * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date and time patterns interpreted in the U.S. locale">
     * <tr bgcolor="#ccccff">
     * <th align=left>Date and Time Pattern
     * <th align=left>Result
     * <tr>
     * <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
     * <p/>
     * <td><code>2001.07.04 AD at 12:08:56 PDT</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>"EEE, MMM d, ''yy"</code>
     * <td><code>Wed, Jul 4, '01</code>
     * <tr>
     * <td><code>"h:mm a"</code>
     * <td><code>12:08 PM</code>
     * <p/>
     * <tr bgcolor="#eeeeff">
     * <td><code>"hh 'o''clock' a, zzzz"</code>
     * <td><code>12 o'clock PM, Pacific Daylight Time</code>
     * <tr>
     * <td><code>"K:mm a, z"</code>
     * <td><code>0:08 PM, PDT</code>
     * <tr bgcolor="#eeeeff">
     * <p/>
     * <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
     * <td><code>02001.July.04 AD 12:08 PM</code>
     * <tr>
     * <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
     * <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
     * <tr bgcolor="#eeeeff">
     * <td><code>"yyMMddHHmmssZ"</code>
     * <p/>
     * <td><code>010704120856-0700</code>
     * </table>
     * @param calendar Calendar representation of the date
     * @param format   format of the date as per example
     * @return String of formatted date, or null
     * @since 1.0
     */
    public static String getFormattedDate(Calendar calendar, String format)
    {
        String out = "";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setCalendar(calendar);
            out = sdf.format(calendar.getTimeInMillis());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(),e);
        }
        return out;
    }

    /**
     * Returns a date formatted in MMDDYYYY,HH:mm:ss format
     * @param calendar Calendar object
     * @return formatted date
     * @since 1.0
     */
    public static String getFormattedDate(Calendar calendar)
    {
        if (calendar == null)
        {
            return "";
        }
        return getFormattedDate(calendar, "MMM dd yyyy, HH:mm:ss");
    }
}
