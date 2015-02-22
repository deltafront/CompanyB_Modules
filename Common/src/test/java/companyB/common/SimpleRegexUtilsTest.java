package companyB.common;

import companyB.common.utils.SimpleRegexUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class SimpleRegexUtilsTest
{
    @Test
    public void testIsLetterSeries()
    {
        assertTrue(SimpleRegexUtils.isLetterSeries("abcde"));
    }

    @Test
    public void testIsLetterSeriesNull()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries(null));
    }

    @Test
    public void testIsNumberTrue()
    {
        char c = '3';
        assertTrue(SimpleRegexUtils.isNumber(c));
    }

    @Test
    public void testIsNumberFalse()
    {
        char c = 'a';
        assertFalse(SimpleRegexUtils.isNumber(c));
    }

    @Test
    public void testIsValidNumberSeriesNull()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries(""));
    }

    @Test
    public void testIsLetterSeriesEmptyString()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries(""));
    }

    @Test
    public void testIsLetterSeriesFalse()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries("asd2"));
    }

    @Test
    public void testIsLetterSeriesFalse_2()
    {
        assertFalse(SimpleRegexUtils.isLetterSeries("asd!"));
    }

    @Test
    public void testIsValidNumberSeriesExponent()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10E7"));
    }

    @Test
    public void testIsValidNumberSeriesExponentLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10e7"));
    }

    @Test
    public void testIsValidNumberSeriesDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10.7"));
    }

    @Test
    public void testIsValidNumberSeriesLeadingDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries(".10"));
    }

    @Test
    public void testIsValidNumberSeriesTrailingDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10."));
    }

    @Test
    public void testIsValidNumberSeriesExponentDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("10.3E7"));
    }

    @Test
    public void testIsValidNumberSeriesNegative()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeDecimal()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeDecimalLeading()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-.10"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeDecimalTrailing()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10."));
    }

    @Test
    public void testIsValidNumberSeriesNegativeExponentLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10e7"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeExponentUpperCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10E7"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeExponentDecimalLowerCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3e7"));
    }

    @Test
    public void testIsValidNumberSeriesNegativeExponentDecimalUpperCase()
    {
        assertTrue(SimpleRegexUtils.isValidNumberSeries("-10.3E7"));
    }

    @Test
    public void testIsValidNumberSeriesDoubleDecimal()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("10.3.7"));
    }

    @Test
    public void testIsValidNumberSeriesDoubleNegative()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("--10"));
    }

    @Test
    public void testIsValidNumberSeriesDoubleExponent()
    {
        assertFalse(SimpleRegexUtils.isValidNumberSeries("10E7e6"));
    }

    @Test
    public void testIsValidFormattedSeriesNullString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, "this"));
    }

    @Test
    public void testIsValidFormattedSeriesEmptyStringString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", "this"));
    }

    @Test
    public void testIsValidFormattedSeriesStringNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-z]", null));
    }

    @Test
    public void testIsValidFormattedSeriesStringEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-z]", ""));
    }

    @Test
    public void testIsValidFormattedSeriesNullEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, ""));
    }

    @Test
    public void testIsValidFormattedSeriesEmptyStringNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", null));
    }

    @Test
    public void testIsValidFormattedSeriesEmptyStringEmptyString()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("", ""));
    }

    @Test
    public void testIsValidFormattedSeriesNullNull()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries(null, null));
    }

    @Test
    public void testIsValidFormattedSeriesComplexLettersFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[", "this"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexLettersPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "this"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexLettersFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "123"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexNumbersPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]*", "12349"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexNumbersFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-8]*", "12349"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexMixedPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "3X9-W7B"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexMixedFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "W7B-3X9"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexEmailPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin@c-a-fe.ca"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexEmailPass_2()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master.jammer@c-a-fe.ca"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexEmailPass_3()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master_jammer@c-a-fe.ca"));
    }

    @Test
    public void testIsValidFormattedSeriesComplexEmailFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin.jaamati.info"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "21701"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipFail()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "2170"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPlusFourPass()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-1733"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_1()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-173"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "2170-1733"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_3()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "217011-17354"));
    }

    @Test
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_4()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701#1733"));
    }

    @Test
    public void testIsValidFormattedSeriesCanadianPostalCodePass_1()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D 9R7"));
    }

    @Test
    public void testIsValidFormattedSeriesCanadianPostalCodePass_2()
    {
        assertTrue(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D-9R7"));
    }

    @Test
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_1()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D9R7"));
    }

    @Test
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_2()
    {
        assertFalse(SimpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "9R7 B4D"));
    }

    @Test
    public void testIsValidAlphaNNull()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries(null));
    }

    @Test
    public void testIsValidAlphaNEmpty()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries(""));
    }

    @Test
    public void testIsValidAlphaNAllAlpha()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("AbC"));
    }

    @Test
    public void testIsValidAlphaNAllN()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("1234567890"));
    }

    @Test
    public void testIsValidAlphaNMixedTrue()
    {
        assertTrue(SimpleRegexUtils.isAlphaNSeries("1234567890AbCDeF"));
    }

    @Test
    public void testIsValidAlphaNMixedFalse()
    {
        assertFalse(SimpleRegexUtils.isAlphaNSeries("1234567890AbCDeF!"));
    }
}
