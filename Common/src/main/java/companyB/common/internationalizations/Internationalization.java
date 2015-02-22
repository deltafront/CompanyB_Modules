package companyB.common.internationalizations;

import java.util.HashMap;

/**
 * This class contains internationalization for days and months in the world's top four Latin-scripted languages.
 *
 * @author C.A. Burrell (deltafront@gmail.com)
 * @version 1.0
 */
@SuppressWarnings("PMD.UselessParentheses")
public abstract class Internationalization
{
    /**
     * Languages that are currently supported:
     * <ul>
     * <li>EN - English</li>
     * <li>ES - Spanish</li>
     * <li>FR - French</li>
     * <li>DE - German</li>
     * </ul>
     *
     * @since 1.0
     */
    public enum Language
    {
        EN, ES, FR, DE
    }

    /**
     * Timeframes currently supported:
     * <ul>
     * <li> Month of the yeay</li>
     * <li> Day of the week</li>
     * </ul>
     *
     * @since 1.0
     */
    public enum TimeFrame
    {
        MONTH_OF_YEAR, DAY_OF_WEEK
    }

    private final static String[] MONTHS_EN = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final static String[] MONTHS_ES = new String[]{"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    private final static String[] MONTHS_FR = new String[]{"janvier", "f�vrier", "mars", "avril", "mai", "juin", "juillet", "ao�t", "septembre", "octobre", "novembre", "d�cembre"};
    private final static String[] MONTHS_DE = new String[]{"Januar", "Februar", "M�rz ", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

    private final static String[] DAYS_EN = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private final static String[] DAYS_ES = new String[]{"lunes", "martes", "mi�rcoles", "jueves", "viernes", "s�bado", "domingo"};
    private final static String[] DAYS_FR = new String[]{"dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"};
    private final static String[] DAYS_DE = new String[]{"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag "};

    private static HashMap<Language, String[]> months;
    private static HashMap<Language, String[]> days;
    private static HashMap<TimeFrame, HashMap<Language, String[]>> map;

    static
    {
        if (months == null)
        {
            months = new HashMap<Language, String[]>();
            months.put(Language.EN, MONTHS_EN);
            months.put(Language.ES, MONTHS_ES);
            months.put(Language.FR, MONTHS_FR);
            months.put(Language.DE, MONTHS_DE);

        }
        if (days == null)
        {
            days = new HashMap<Language, String[]>();
            days.put(Language.EN, DAYS_EN);
            days.put(Language.ES, DAYS_ES);
            days.put(Language.FR, DAYS_FR);
            days.put(Language.DE, DAYS_DE);
        }
        if (map == null)
        {
            map = new HashMap<TimeFrame, HashMap<Language, String[]>>();
            map.put(TimeFrame.DAY_OF_WEEK, days);
            map.put(TimeFrame.MONTH_OF_YEAR, months);
        }
    }


    /**
     * Returns the desired day of the week or month in the indicated language
     *
     * @param number_of_day_or_month zero-based number of the day or month desired. Providing <strong>0</strong> as a value for <em>months</em> will return <u>January</u>, whereas for <em>days</em> will return <u>Sunday</u> (English).
     * @param timeframe              TimeFrame.MONTH_OF_YEAR or TimeFrame.DAY_OF_WEEK
     * @param language               Which of the supported languages to return the value in. One of the following:
     *                               <ul>
     *                               <li>Language.EN (English)</li>
     *                               <li>Language.ES (Spanish)</li>
     *                               <li>Language.FR (French)</li>
     *                               <li>Languare.DE (German)</li>
     *                               </ul>
     * @return String containing the desired day or month in the language desired. If this value is less than zero or exceeds 12 (for months) or 7 (for days of week), a null string will be returned.
     * @since 1.0
     */
    public static String getTimeFrame(int number_of_day_or_month, TimeFrame timeframe, Language language)
    {
        return (number_of_day_or_month < 0 || number_of_day_or_month >= map.get(timeframe).get(language).length) ?
                null : map.get(timeframe).get(language)[number_of_day_or_month].trim();
    }
}
