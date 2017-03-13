package companyB.common.constants;

import java.text.SimpleDateFormat;

/**
 * Somewhat useful date constants.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public enum DateConstants
{
    ISO_8601("yyyy-MM-dd'T'HH:mm:ssX");
    private SimpleDateFormat simpleDateFormat;
    DateConstants(String dateFormat)
    {
       this.simpleDateFormat= new SimpleDateFormat(dateFormat);
    }
    public SimpleDateFormat dateFormat()
    {
        return this.simpleDateFormat;
    }
}
