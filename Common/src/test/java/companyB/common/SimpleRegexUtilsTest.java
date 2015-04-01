package companyB.common;

import companyB.common.utils.SimpleRegexUtils;
import org.testng.annotations.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@Test(groups = {"unit","simple.regex.utils","common"})
public class SimpleRegexUtilsTest
{
    
    public void testIsLetterSeries()
    {
        assertTrue(SimpleRegexUtils.isLetterSeries("abcde"));
    }

    
    public void testIsLetterSeriesNull()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries(null));
    }

    
    public void testIsNumberTrue()
    {
        char c = '3';
        assertTrue(SimpleRegexUtils.isNumber(c));
    }

    
    public void testIsNumberFalse()
    {
        char c = 'a';
        assertFalse(SimpleRegexUtils.isNumber(c));
    }

    
    public void testIsValidNumberSeriesNull()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries(""));
    }

    
    public void testIsLetterSeriesEmptyString()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries(""));
    }

    
    public void testIsLetterSeriesFalse()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries("asd2"));
    }

    
    public void testIsLetterSeriesFalse_2()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries("asd!"));
    }

    
    public void testIsValidNumberSeriesExponent()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10E7"));
    }

    
    public void testIsValidNumberSeriesExponentLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10e7"));
    }

    
    public void testIsValidNumberSeriesDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10.7"));
    }

    
    public void testIsValidNumberSeriesLeadingDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries(".10"));
    }

    
    public void testIsValidNumberSeriesTrailingDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10."));
    }

    
    public void testIsValidNumberSeriesExponentDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10.3E7"));
    }

    
    public void testIsValidNumberSeriesNegative()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimalLeading()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-.10"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimalTrailing()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10."));
    }

    
    public void testIsValidNumberSeriesNegativeExponentLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10e7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentUpperCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10E7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentDecimalLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3e7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentDecimalUpperCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3E7"));
    }

    
    public void testIsValidNumberSeriesDoubleDecimal()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("10.3.7"));
    }

    
    public void testIsValidNumberSeriesDoubleNegative()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("--10"));
    }

    
    public void testIsValidNumberSeriesDoubleExponent()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("10E7e6"));
    }

    
    public void testIsValidFormattedSeriesNullString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, "this"));
    }

    
    public void testIsValidFormattedSeriesEmptyStringString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", "this"));
    }

    
    public void testIsValidFormattedSeriesStringNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-z]", null));
    }

    
    public void testIsValidFormattedSeriesStringEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-z]", ""));
    }

    
    public void testIsValidFormattedSeriesNullEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, ""));
    }

    
    public void testIsValidFormattedSeriesEmptyStringNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", null));
    }

    
    public void testIsValidFormattedSeriesEmptyStringEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", ""));
    }

    
    public void testIsValidFormattedSeriesNullNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, null));
    }

    
    public void testIsValidFormattedSeriesComplexLettersFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[", "this"));
    }

    
    public void testIsValidFormattedSeriesComplexLettersPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "this"));
    }

    
    public void testIsValidFormattedSeriesComplexLettersFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "123"));
    }

    
    public void testIsValidFormattedSeriesComplexNumbersPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]*", "12349"));
    }

    
    public void testIsValidFormattedSeriesComplexNumbersFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-8]*", "12349"));
    }

    
    public void testIsValidFormattedSeriesComplexMixedPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "3X9-W7B"));
    }

    
    public void testIsValidFormattedSeriesComplexMixedFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "W7B-3X9"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass_2()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master.jammer@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass_3()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master_jammer@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin.jaamati.info"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "21701"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "2170"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-1733"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_1()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-173"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "2170-1733"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_3()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "217011-17354"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_4()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701#1733"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodePass_1()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D 9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodePass_2()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D-9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_1()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "9R7 B4D"));
    }

    
    public void testIsValidAlphaNNull()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries(null));
    }

    
    public void testIsValidAlphaNEmpty()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries(""));
    }

    
    public void testIsValidAlphaNAllAlpha()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("AbC"));
    }

    
    public void testIsValidAlphaNAllN()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("1234567890"));
    }

    
    public void testIsValidAlphaNMixedTrue()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("1234567890AbCDeF"));
    }

    
    public void testIsValidAlphaNMixedFalse()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries("1234567890AbCDeF!"));
    }
}
