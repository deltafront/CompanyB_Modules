package companyB.common.enums;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides access to months, their order in the calendar year (1 based),their abbreviations, and days
 *
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public enum Months
{
    January(1, 31, "Jan"),
    February(2, 28, "Feb"),
    March(3, 31, "Mar"),
    April(4, 30, "Apr"),
    May(5, 31, "May"),
    June(6, 30, "Jun"),
    July(7, 31, "Jul"),
    August(8, 31, "Aug"),
    September(9, 30, "Sep"),
    October(10, 31, "Oct"),
    November(11, 30, "Nov"),
    December(12, 31, "Dec");
    public int days;
    public String abbreviation;
    public int month_in_year;

    private Months(int month_in_year, int days, String abbreviation)
    {
        this.month_in_year = month_in_year;
        this.days = days;
        this.abbreviation = abbreviation;
        if (this.abbreviation.equals("Feb"))
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.MONTH, Calendar.FEBRUARY);
            //code for testing this method
            //cal.set( Calendar.YEAR,  2008 );
            this.days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * Returns the month represented by the integer (1 = January / Jan). This method assumes a 1-base.
     *
     * @param month_in_year month in year to get string for.
     * @param abbreviation  true if you want the the abbreviated month
     * @return Month represented by the integer (1-based)
     * @since 1.0
     */
    public static String getMonth(int month_in_year, boolean abbreviation)
    {
        return getMonth(month_in_year, false, abbreviation);
    }

    /**
     * Returns the month represented by the integer (1 = January / Jan). If zero-based is set to true, then
     * 0 = January / Jan.
     *
     * @param month_in_year month in year to get string for.
     * @param zero_based    true if you are using zero as the first number
     * @param abbreviation  true if you want the the abbreviated month
     * @return Month represented by the integer
     * @since 1.0
     */
    public static String getMonth(int month_in_year, boolean zero_based, boolean abbreviation)
    {
        String month = "";
        if ((zero_based) && (month_in_year > -1 && month_in_year < 12)
                || (!zero_based) && (month_in_year > 0 && month_in_year < 13))
        {
            for (Months m : Months.values())
            {
                if (month_in_year == ((zero_based) ? m.month_in_year - 1 : m.month_in_year))
                {
                    month += (abbreviation) ? m.abbreviation : m.name();
                    break;
                }
            }
        }
        return month;
    }

    /**
     * Returns a Set of months of the year ordered by their occurrence.
     *
     * @param abbreviated Setting this to true will fill the set with abbreviations.
     * @return Set of months of the year
     * @since 1.0
     */
    public static Set<String> getMonths(boolean abbreviated)
    {
        Set<String> months = new HashSet<String>();
        for (Months month : Months.values())
        {
            if (abbreviated)
            {
                months.add(month.abbreviation);
            }
            else
            {
                months.add(month.name());
            }
        }
        return months;
    }
}