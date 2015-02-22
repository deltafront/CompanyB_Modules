/**
 Copyright (c) 2010, C.A. Burrell
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.
 * Neither the name of "c-a-fe.ca" nor the names of its contributors may be used to endorse or promote products derived from this
 software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS
 BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 */
/**
 * @author C.A. Burrell (deltafront@gmail.com)
 */

package companyB.common;

import companyB.common.enums.Days;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;


public class DaysTest
{
    @Test
    public void getDayFullOneOffset()
    {
        assertEquals("Wednesday", Days.getDayOfWeek(4, false, false));
    }

    @Test
    public void getDayFullZeroOffset()
    {
        assertEquals("Wednesday", Days.getDayOfWeek(3, true, false));
    }

    @Test
    public void getDayAbbrOneOffset()
    {
        assertEquals("Wed", Days.getDayOfWeek(4, false, true));
    }

    @Test
    public void getDayAbbrZeroOffset()
    {
        assertEquals("Wed", Days.getDayOfWeek(3, true, true));
    }

    @Test
    public void getInvalidNumberDayFull()
    {
        assertEquals("", Days.getDayOfWeek(-1, true, false));
        assertEquals("", Days.getDayOfWeek(12, true, false));
        assertEquals("", Days.getDayOfWeek(0, false, false));
        assertEquals("", Days.getDayOfWeek(13, false, false));
    }

    @Test
    public void getInvalidNumberDayAbbr()
    {
        assertEquals("", Days.getDayOfWeek(-1, true, true));
        assertEquals("", Days.getDayOfWeek(12, true, true));
        assertEquals("", Days.getDayOfWeek(0, false, true));
        assertEquals("", Days.getDayOfWeek(13, false, true));
    }

    @Test
    public void getSetFull()
    {
        Set<String> days = Days.getDays(false);
        assertEquals(7, days.size());
        for (String day : days)
        {
            assertTrue("Day '" + day + "' is an abbreviation.", day.length() > 3);
        }
    }

    @Test
    public void getSetAbbr()
    {
        Set<String> days = Days.getDays(true);
        assertEquals(7, days.size());
        for (String day : days)
        {
            assertTrue("Day '" + day + "' is not an abbreviation.", day.length() == 3);
        }
    }

    @Test
    public void getValues()
    {
        for (Days day : Days.values())
        {
            assertNotNull(Days.valueOf(day.name()));
        }
    }

    @Test
    public void getDayOfWeekFull()
    {
        assertEquals("Monday", Days.getDayOfWeek(2, false));
    }

    @Test
    public void getDayOfWeekAbbreviation()
    {
        assertEquals("Mon", Days.getDayOfWeek(2, true));
    }

}
