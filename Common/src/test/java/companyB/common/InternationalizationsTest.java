package companyB.common;

import companyB.common.internationalizations.Internationalization;
import org.junit.After;
import org.junit.Test;

import static companyB.common.internationalizations.Internationalization.getTimeFrame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InternationalizationsTest
{
    private String day_or_month;

    @After
    public void after()
    {
        day_or_month = null;
    }

    @Test
    public void EnglishMonday()
    {
        day_or_month = getTimeFrame(1, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.EN);
        assertEquals("Monday", day_or_month);
    }

    @Test
    public void SpanishTuesday()
    {
        day_or_month = getTimeFrame(1, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.ES);
        assertEquals("martes", day_or_month);
    }

    @Test
    public void FrenchWednesday()
    {
        day_or_month = getTimeFrame(3, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.FR);
        assertEquals("mercredi", day_or_month);
    }

    @Test
    public void GermanThursday()
    {
        day_or_month = getTimeFrame(3, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.DE);
        assertEquals("Donnerstag", day_or_month);
    }

    @Test
    public void SpanishFriday()
    {
        day_or_month = getTimeFrame(4, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.ES);
        assertEquals("viernes", day_or_month);
    }

    @Test
    public void FrenchSaturday()
    {
        day_or_month = getTimeFrame(6, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.FR);
        assertEquals("samedi", day_or_month);
    }

    @Test
    public void GermanSunday()
    {
        day_or_month = getTimeFrame(6, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.DE);
        assertEquals("Sonntag", day_or_month);
    }

    @Test
    public void DayOutOfBounds()
    {
        day_or_month = getTimeFrame(7, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.DE);
        assertNull(day_or_month);
    }

    @Test
    public void DayOutMinusOne()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.DAY_OF_WEEK, Internationalization.Language.DE);
        assertNull(day_or_month);
    }

    @Test
    public void EnglishJanuary()
    {
        day_or_month = getTimeFrame(0, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("January", day_or_month);
    }

    @Test
    public void SpanishFebruary()
    {
        day_or_month = getTimeFrame(1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.ES);
        assertEquals("febrero", day_or_month);
    }

    @Test
    public void FrenchMarch()
    {
        day_or_month = getTimeFrame(2, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.FR);
        assertEquals("mars", day_or_month);
    }

    @Test
    public void GermanApril()
    {
        day_or_month = getTimeFrame(3, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.DE);
        assertEquals("April", day_or_month);
    }

    @Test
    public void EnglishMay()
    {
        day_or_month = getTimeFrame(4, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("May", day_or_month);
    }

    @Test
    public void SpanishJune()
    {
        day_or_month = getTimeFrame(5, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.ES);
        assertEquals("junio", day_or_month);
    }

    @Test
    public void FrenchJuly()
    {
        day_or_month = getTimeFrame(6, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.FR);
        assertEquals("juillet", day_or_month);
    }

    @Test
    public void GermanAugust()
    {
        day_or_month = getTimeFrame(7, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.DE);
        assertEquals("August", day_or_month);
    }

    @Test
    public void EnglishSeptember()
    {
        day_or_month = getTimeFrame(8, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("September", day_or_month);
    }

    @Test
    public void SpanishOctober()
    {
        day_or_month = getTimeFrame(9, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.ES);
        assertEquals("octubre", day_or_month);
    }

    @Test
    public void FrenchNovember()
    {
        day_or_month = getTimeFrame(10, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.FR);
        assertEquals("novembre", day_or_month);
    }

    @Test
    public void GermanDecember()
    {
        day_or_month = getTimeFrame(11, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.DE);
        assertEquals("Dezember", day_or_month);
    }

    @Test
    public void MonthOutOfBounds()
    {
        day_or_month = getTimeFrame(12, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.DE);
        assertNull(day_or_month);
    }

    @Test
    public void MonthOutMinusOne()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.DE);
        assertNull(day_or_month);
    }

    /**
     * tests  below this point are for future expansion. So as to allow plenty of room, just do one day and two months per new language.
     */

    public void Monday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Tuesday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Wednesday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Thursday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Friday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Saturday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void Sunday()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void January()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void February()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void March()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void April()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void May()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void June()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void July()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void August()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void September()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void October()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void November()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }

    public void December()
    {
        day_or_month = getTimeFrame(-1, Internationalization.TimeFrame.MONTH_OF_YEAR, Internationalization.Language.EN);
        assertEquals("", day_or_month);
    }
}
