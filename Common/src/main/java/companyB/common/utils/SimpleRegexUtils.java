package companyB.common.utils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Contains utilities for evaluation of string content
 *
 * @author C.A. Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class SimpleRegexUtils
{
    /**
     * Tests to see if this string consists entirely of alphanumeric characters
     *
     * @param string String to be evaluated.
     * @return true if this string consists entirely of alphanumeric characters
     * @since 1.0
     */
    public static boolean isAlphaNSeries(String string)
    {
        if (isEmpty(string))
        {
            return false;
        }
        for (int i = 0; i < string.length(); i++)
        {
            char _char = string.toLowerCase().charAt(i);
            if (!isNumber(_char) && !isLetter(_char))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests to see if the string consists entirely of letters
     *
     * @param string String to be evaluated.
     * @return true if the string consists entirely of letters
     * @since 1.0
     */
    public static boolean isLetterSeries(String string)
    {
        if (isEmpty(string))
        {
            return false;
        }
        for (int i = 0; i < string.length(); i++)
        {
            if (!isLetter(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the character represents a alphabetic character.
     * @param _char character to be evaluated.
     * @return True if char is a letter.
     * @since 1.0
     */
    public static boolean isLetter(char _char)
    {
        return Character.isLetter(_char);
    }

    /**
     * Determines if the character represents a numeric character.
     * @param _char character to be evaluated.
     * @return True if char is a number.
     * @since 1.0
     */
    public static boolean isNumber(char _char)
    {
        return Character.isDigit(_char);
    }

    /**
     * Determines whether this string is comprised of all numerical digits
     *
     * @param string String to be evaluated.
     * @return true if this string represents a valid number series
     * @since 1.0
     */
    @SuppressWarnings("UnnecessaryContinue")
    public static boolean isValidNumberSeries(String string)
    {
        if (isEmpty(string))
        {
            return false;
        }
        boolean isValid = true;
        boolean isESeries = string.toLowerCase().contains("e");
        char[] chars = string.toCharArray();
        boolean eflag = false;
        int negcount = 0;
        boolean decimalflag = false;
        for (char c : chars)
        {
            if (c == '-' && (isESeries) ? negcount < 2 : negcount < 1)
            {
                negcount++;
                continue;
            }
            else if ((c == 'E' || c == 'e') && !eflag)
            {
                eflag = true;
                continue;
            }
            else if (c == '.' && !decimalflag)
            {
                decimalflag = true;
                continue;
            }
            else if (!Character.isDigit(c))
            {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    /**
     * Determines if the input string conforms to the format string. The format string must be a valid regular expression,
     *
     * @param format format string
     * @param input  string that is supposed to adhere to format string
     * @return true if input is of 'format' format
     * @since 1.0
     */
    public static boolean isValidFormattedSeries(String format, String input)
    {
        if (isEmpty(format) || isEmpty(input))
        {
            return false;
        }
        boolean isValid = true;
        try
        {
            isValid = Pattern.matches(format, input);
        }
        catch (PatternSyntaxException e)
        {
            isValid = false;
            e.printStackTrace();
        }
        return isValid;
    }
}