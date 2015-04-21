package companyB.common.constants;

import java.text.SimpleDateFormat;

/**
 * @author Charles Burrell (deltafront@gmail.com)
 * @since  1.1.0
 */
public class DateConstants
{
    public final static String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
    public final static SimpleDateFormat ISO_8601 = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
}
