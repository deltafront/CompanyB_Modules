package companyB.common.test;

import companyB.common.utils.SimpleRegexUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@Test(groups = {"unit","simple.regex.utils","common"})
public class SimpleRegexUtilsTest
{
    private SimpleRegexUtils simpleRegexUtils;
    @BeforeMethod
    public void before()
    {
        simpleRegexUtils = new SimpleRegexUtils();
    }
    public void testIsLetterSeries()
    {
        assertTrue(simpleRegexUtils.isLetterSeries("abcde"));
    }

    
    public void testIsLetterSeriesNull()
    {
        assertFalse(simpleRegexUtils.isLetterSeries(null));
    }

    
    public void testIsNumberTrue()
    {
        char c = '3';
        assertTrue(simpleRegexUtils.isNumber(c));
    }

    
    public void testIsNumberFalse()
    {
        char c = 'a';
        assertFalse(simpleRegexUtils.isNumber(c));
    }

    
    public void testIsValidNumberSeriesNull()
    {
        assertFalse(simpleRegexUtils.isValidNumberSeries(""));
    }

    
    public void testIsLetterSeriesEmptyString()
    {
        assertFalse(simpleRegexUtils.isLetterSeries(""));
    }

    
    public void testIsLetterSeriesFalse()
    {
        assertFalse(simpleRegexUtils.isLetterSeries("asd2"));
    }

    
    public void testIsLetterSeriesFalse_2()
    {
        assertFalse(simpleRegexUtils.isLetterSeries("asd!"));
    }

    
    public void testIsValidNumberSeriesExponent()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("10E7"));
    }

    
    public void testIsValidNumberSeriesExponentLowerCase()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("10e7"));
    }

    
    public void testIsValidNumberSeriesDecimal()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("10.7"));
    }

    
    public void testIsValidNumberSeriesLeadingDecimal()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries(".10"));
    }

    
    public void testIsValidNumberSeriesTrailingDecimal()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("10."));
    }

    
    public void testIsValidNumberSeriesExponentDecimal()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("10.3E7"));
    }

    
    public void testIsValidNumberSeriesNegative()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimal()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10.3"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimalLeading()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-.10"));
    }

    
    public void testIsValidNumberSeriesNegativeDecimalTrailing()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10."));
    }

    
    public void testIsValidNumberSeriesNegativeExponentLowerCase()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10e7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentUpperCase()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10E7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentDecimalLowerCase()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10.3e7"));
    }

    
    public void testIsValidNumberSeriesNegativeExponentDecimalUpperCase()
    {
        assertTrue(simpleRegexUtils.isValidNumberSeries("-10.3E7"));
    }

    
    public void testIsValidNumberSeriesDoubleDecimal()
    {
        assertFalse(simpleRegexUtils.isValidNumberSeries("10.3.7"));
    }

    
    public void testIsValidNumberSeriesDoubleNegative()
    {
        assertFalse(simpleRegexUtils.isValidNumberSeries("--10"));
    }

    
    public void testIsValidNumberSeriesDoubleExponent()
    {
        assertFalse(simpleRegexUtils.isValidNumberSeries("10E7e6"));
    }

    
    public void testIsValidFormattedSeriesNullString()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries(null, "this"));
    }

    
    public void testIsValidFormattedSeriesEmptyStringString()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("", "this"));
    }

    
    public void testIsValidFormattedSeriesStringNull()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[a-z]", null));
    }

    
    public void testIsValidFormattedSeriesStringEmptyString()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[a-z]", ""));
    }

    
    public void testIsValidFormattedSeriesNullEmptyString()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries(null, ""));
    }

    
    public void testIsValidFormattedSeriesEmptyStringNull()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("", null));
    }

    
    public void testIsValidFormattedSeriesEmptyStringEmptyString()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("", ""));
    }

    
    public void testIsValidFormattedSeriesNullNull()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries(null, null));
    }

    
    public void testIsValidFormattedSeriesComplexLettersFail_2()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[", "this"));
    }

    
    public void testIsValidFormattedSeriesComplexLettersPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "this"));
    }

    
    public void testIsValidFormattedSeriesComplexLettersFail()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z]*", "123"));
    }

    
    public void testIsValidFormattedSeriesComplexNumbersPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[0-9]*", "12349"));
    }

    
    public void testIsValidFormattedSeriesComplexNumbersFail()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-8]*", "12349"));
    }

    
    public void testIsValidFormattedSeriesComplexMixedPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "3X9-W7B"));
    }

    
    public void testIsValidFormattedSeriesComplexMixedFail()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{1}[A-Z]{1}[0-9]{1}\\-[A-z]{1}[0-9]{1}[A-Z]{1}", "W7B-3X9"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass_2()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master.jammer@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailPass_3()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "master_jammer@c-a-fe.ca"));
    }

    
    public void testIsValidFormattedSeriesComplexEmailFail()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[a-zA-Z0-9._%+-]*\\@[-a-zA-Z0-9._%+-]*\\.[a-zA-Z]{2,6}", "admin.jaamati.info"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "21701"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipFail()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}", "2170"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourPass()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-1733"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_1()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701-173"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_2()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "2170-1733"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_3()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "217011-17354"));
    }

    
    public void testIsValidFormattedSeriesAmericanZipPlusFourFail_4()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", "21701#1733"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodePass_1()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D 9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodePass_2()
    {
        assertTrue(simpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D-9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_1()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "B4D9R7"));
    }

    
    public void testIsValidFormattedSeriesCanadianPostalCodeFail_2()
    {
        assertFalse(simpleRegexUtils.isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", "9R7 B4D"));
    }

    
    public void testIsValidAlphaNNull()
    {
        assertFalse(simpleRegexUtils.isAlphaNSeries(null));
    }

    
    public void testIsValidAlphaNEmpty()
    {
        assertFalse(simpleRegexUtils.isAlphaNSeries(""));
    }

    
    public void testIsValidAlphaNAllAlpha()
    {
        assertTrue(simpleRegexUtils.isAlphaNSeries("AbC"));
    }

    
    public void testIsValidAlphaNAllN()
    {
        assertTrue(simpleRegexUtils.isAlphaNSeries("1234567890"));
    }

    
    public void testIsValidAlphaNMixedTrue()
    {
        assertTrue(simpleRegexUtils.isAlphaNSeries("1234567890AbCDeF"));
    }

    
    public void testIsValidAlphaNMixedFalse()
    {
        assertFalse(simpleRegexUtils.isAlphaNSeries("1234567890AbCDeF!"));
    }
}
