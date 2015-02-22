package companyB.common;

import companyB.common.enums.Months;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class MonthsTest
{

    @Test
    public void getMonthAbbr()
    {
        assertEquals("Jan", Months.getMonth(1, true));
    }

    @Test
    public void getMonthFull()
    {
        assertEquals("January", Months.getMonth(1, false));
    }

    @Test
    public void getMonthFullOneOffset()
    {
        assertEquals("August", Months.getMonth(8, false, false));
    }

    @Test
    public void getMonthFullZeroOffset()
    {
        assertEquals("August", Months.getMonth(7, true, false));
    }

    @Test
    public void getMonthAbbrOneOffset()
    {
        assertEquals("August", Months.getMonth(8, false, false));
    }

    @Test
    public void getMonthAbbrZeroOffset()
    {
        assertEquals("August", Months.getMonth(7, true, false));
    }

    @Test
    public void getInvalidNumberMonthFull()
    {
        assertEquals("", Months.getMonth(-1, true, false));
        assertEquals("", Months.getMonth(12, true, false));
        assertEquals("", Months.getMonth(0, false, false));
        assertEquals("", Months.getMonth(13, false, false));
    }

    @Test
    public void getInvalidNumberMonthAbbr()
    {
        assertEquals("", Months.getMonth(-1, true, true));
        assertEquals("", Months.getMonth(12, true, true));
        assertEquals("", Months.getMonth(0, false, true));
        assertEquals("", Months.getMonth(13, false, true));
    }

    @Test
    public void getSetFull()
    {
        Set<String> months = Months.getMonths(false);
        assertEquals(12, months.size());
        for (String month : months)
        {
            assertTrue("Month '" + month + "' is an abbreviation.", month.length() >= 3);
        }
    }

    @Test
    public void getSetAbbr()
    {
        Set<String> months = Months.getMonths(true);
        assertEquals(12, months.size());
        for (String month : months)
        {
            assertTrue("Month '" + month + "' is not an abbreviation.", month.length() == 3);
        }
    }

    @Test
    public void getValues()
    {
        for (Months month : Months.values())
        {
            assertNotNull(Months.valueOf(month.name()));
        }
    }
}
