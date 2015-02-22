package companyB.common.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * Days of week, abbreviation, and position of day in week ( Sunday = 1, Saturday = 7 )
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public enum Days
{
    Sunday("Sun", 1),
    Monday("Mon", 2),
    Tuesday("Tue", 3),
    Wednesday("Wed", 4),
    Thursday("Thu", 5),
    Friday("Fri", 6),
    Saturday("Sat", 7);
    public String abbreviation;
    public int day_of_week;

    private Days(String abbreviation, int day_of_week)
    {
        this.abbreviation = abbreviation;
        this.day_of_week = day_of_week;
    }

    /**
     * Returns the day represented by the integer (1 = Sunday / Sun). This method assumes a 1-base.
     * @param day_of_week integer to get day of week for.
     * @param abbreviation true if you want the the abbreviated day
     * @return Day represented by the integer (1-based)
     * @since 1.0
     */
    public static String getDayOfWeek(int day_of_week, boolean abbreviation)
    {
        return getDayOfWeek(day_of_week, false, abbreviation);
    }

    /**
     * Returns the day represented by the integer (1 = Monday / Mon). If zero-based is set to true, then
     * 0 = Monday / Mon.
     *
     * @param day_of_week integer to get day of week for.
     * @param zero_based   - true if you are using zero as the first number
     * @param abbreviation true if you want the the abbreviated day
     * @return Day represented by the integer
     * @since 1.0
     */
    public static String getDayOfWeek(int day_of_week, boolean zero_based, boolean abbreviation)
    {
        String day = "";
        if (zero_based && (day_of_week > -1 && day_of_week < 6)
                || !zero_based && (day_of_week > 1 && day_of_week < 7))
        {
            for (Days d : Days.values())
            {
                if (day_of_week == ((zero_based) ? d.day_of_week - 1 : d.day_of_week))
                {
                    day += abbreviation ? d.abbreviation : d.name();
                    break;
                }
            }
        }
        return day;
    }

    /**
     * Returns a Set of days of the week ordered by their occurrence.
     * @param abbreviated Setting this to true will fill the set with abbreviations.
     * @return Set of days of the week
     * @since 1.0
     */
    public static Set<String> getDays(boolean abbreviated)
    {
        Set<String> days = new HashSet<String>();
        for (Days day : Days.values())
        {
            if (abbreviated)
            {
                days.add(day.abbreviation);
            }
            else
            {
                days.add(day.name());
            }
        }
        return days;
    }
}
