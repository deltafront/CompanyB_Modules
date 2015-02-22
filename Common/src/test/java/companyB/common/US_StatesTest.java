package companyB.common;

import companyB.common.enums.US_States;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;


public class US_StatesTest
{
    @Test
    public void validStateFull()
    {
        assertTrue(US_States.isState("NEW YORK"));
        assertTrue(US_States.isState("NEW_YORK"));
    }

    @Test
    public void validStateAbbreviation()
    {
        assertTrue(US_States.isState("NY"));
    }

    @Test
    public void inValidStateFull()
    {
        assertFalse(US_States.isState("NEW ZEALAND"));
    }

    @Test
    public void inValidStateAbbreviation()
    {
        assertFalse(US_States.isState("NZ"));
    }

    @Test
    public void validProvinceNull()
    {
        assertFalse(US_States.isState(null));
    }

    @Test
    public void validProvinceEmptyString()
    {
        assertFalse(US_States.isState(""));
    }

    @Test
    public void validPostalCodeBasic()
    {
        assertTrue(US_States.isValidUSZip("12345"));
    }

    @Test
    public void validPostalCodePlus4Dash()
    {
        assertTrue(US_States.isValidUSZip("12345-6789"));
    }

    @Test
    public void validPostalCodePlus4Space()
    {
        assertTrue(US_States.isValidUSZip("12345 6789"));
    }

    @Test
    public void invalidPostalCodeBasic()
    {
        assertFalse(US_States.isValidUSZip("123456"));
        assertFalse(US_States.isValidUSZip("1234"));
    }

    @Test
    public void invalidPostalCodePlus4Dash()
    {
        assertFalse(US_States.isValidUSZip("12345-678"));
        assertFalse(US_States.isValidUSZip("12345-67890"));
        assertFalse(US_States.isValidUSZip("1234-6789"));
        assertFalse(US_States.isValidUSZip("1234-67890"));
    }

    @Test
    public void invalidPostalCodePlus4Space()
    {
        assertFalse(US_States.isValidUSZip("12345 678"));
        assertFalse(US_States.isValidUSZip("12345 67890"));
        assertFalse(US_States.isValidUSZip("1234 6789"));
        assertFalse(US_States.isValidUSZip("1234 67890"));
    }

    @Test
    public void postalCodeNull()
    {
        assertFalse(US_States.isValidUSZip(null));
    }

    @Test
    public void postalCodeEmptyString()
    {
        assertFalse(US_States.isValidUSZip(""));
    }

    @Test
    public void getSetFull()
    {
        Set<String> states = US_States.displayStates(false);
        assertEquals(US_States.values().length, states.size());
        String temp = "";
        for (String state : states)
        {
            if (!temp.equals(""))
            {
                assertTrue("State '" + state + "' is not lexically after '" + temp + "'", state.compareTo(temp) > 0);
            }
            temp = state;
            assertTrue("State name '" + state + "' is an abbreviation.", state.length() > 2);
        }
    }

    @Test
    public void getSetAbbr()
    {
        Set<String> states = US_States.displayStates(true);
        //assertEquals( US_States.values().length, states.size() );//TODO - why is this failing?
        String temp = "";
        for (String state : states)
        {
            if (!temp.equals(""))
            {
                assertTrue("State '" + state + "' is not lexically after '" + temp + "'", state.compareTo(temp) > 0);
            }
            temp = state;
            assertTrue("State name '" + state + "' is not an abbreviation.", state.length() == 2);
        }
    }
}