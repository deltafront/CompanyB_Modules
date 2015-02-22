package companyB.common;

import companyB.common.enums.Canadian_Provinces;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CanadianProvincesTest
{
    @Test
    public void validProvinceFull()
    {
        assertTrue(Canadian_Provinces.isProvince("NEW_BRUNSWICK"));
        assertTrue(Canadian_Provinces.isProvince("NEW BRUNSWICK"));
    }

    @Test
    public void validProvinceAbbreviation()
    {
        assertTrue(Canadian_Provinces.isProvince("AB"));
    }

    @Test
    public void inValidProvinceFull()
    {
        assertFalse(Canadian_Provinces.isProvince("New_Zealand"));
        assertFalse(Canadian_Provinces.isProvince("New Zealand"));
    }

    @Test
    public void inValidProvinceAbbreviation()
    {
        assertFalse(Canadian_Provinces.isProvince("NZ"));
    }

    @Test
    public void validProvinceNull()
    {
        assertFalse(Canadian_Provinces.isProvince(null));
    }

    @Test
    public void validProvinceEmptyString()
    {
        assertFalse(Canadian_Provinces.isProvince(""));
    }

    @Test
    public void validPostalCodeDash()
    {
        assertTrue(Canadian_Provinces.isValidCandianPostalCode("A2A-3B3"));
    }

    @Test
    public void validPostalCodeSpace()
    {
        assertTrue(Canadian_Provinces.isValidCandianPostalCode("A2A 3B3"));
    }

    @Test
    public void inValidPostalCodeDash()
    {
        assertFalse(Canadian_Provinces.isValidCandianPostalCode("a2a-3b3"));
        assertFalse(Canadian_Provinces.isValidCandianPostalCode("2A2-B3B"));
    }

    @Test
    public void inValidPostalCodeSpace()
    {
        assertFalse(Canadian_Provinces.isValidCandianPostalCode("a2a 3b3"));
        assertFalse(Canadian_Provinces.isValidCandianPostalCode("2A2 B3B"));
    }

    @Test
    public void postalCodeNull()
    {
        assertFalse(Canadian_Provinces.isValidCandianPostalCode(null));
    }

    @Test
    public void postalCodeEmptyString()
    {
        assertFalse(Canadian_Provinces.isValidCandianPostalCode(""));
    }

    @Test
    public void getSetFull()
    {
        Set<String> provinces = Canadian_Provinces.displayProvinces(false);
        assertEquals(Canadian_Provinces.values().length, provinces.size());
        String temp = "";
        for (String province : provinces)
        {
            if (!temp.equals(""))
            {
                assertTrue("Province '" + province + "' is not lexically after '" + temp + "'", province.compareTo(temp) > 0);
            }
            temp = province;
            assertTrue("Province name '" + province + "' is an abbreviation.", province.length() > 2);
        }
    }

    @Test
    public void getSetAbbr()
    {
        Set<String> provinces = Canadian_Provinces.displayProvinces(true);
        assertEquals(Canadian_Provinces.values().length, provinces.size());
        String temp = "";
        for (String province : provinces)
        {
            if (!temp.equals(""))
            {
                assertTrue("Province '" + province + "' is not lexically after '" + temp + "'", province.compareTo(temp) > 0);
            }
            temp = province;
            assertTrue("Province name '" + province + "' is not an abbreviation.", province.length() == 2);
        }
    }
}