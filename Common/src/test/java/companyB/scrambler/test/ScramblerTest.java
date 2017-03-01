package companyB.scrambler.test;


import companyB.scrambler.Scrambler;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

@Test(groups = {"unit","scrambler"})
public class ScramblerTest
{
    private final String vowels = "aeiouy";
    private final String consonants = "bcdfghjklmnpqrstvwxyz";
    public void testScrambledStringNotNull()
    {
        final Scrambler scrambler = new Scrambler();
        final String scrambled = scrambler.getScrambledString();
        assertNotNull(scrambled);
        assureDashIsPresent(scrambled);
        assureThreeOrFourCharsAfterDash(scrambled);
    }
    public void testReset()
    {
        final Scrambler scrambler = new Scrambler();
        String scrambled = scrambler.getScrambledString();
        assertThat(scrambled,not(nullValue()));
        scrambler.reset();
        scrambled = scrambler.getScrambledString();
        assertThat(scrambled,not(nullValue()));

    }
    private void assureDashIsPresent(String output)
    {
        assertThat(output, containsString("-"));
    }
    private void assureThreeOrFourCharsAfterDash(String output)
    {
        final String[]split = output.split("-");
        assertTrue(split.length == 2);
        final String before = split[0];
        final String after = split[1];
        assertThat(after.length(),anyOf(equalTo(3),equalTo(4)));
        assureCharsAfterDashAllNums(after);
        assureEvensAreConsonantsOddsAreVowels(before);
    }
    private void assureCharsAfterDashAllNums(String afterDash)
    {
        final Pattern digitPattern = Pattern.compile("\\d{3,4}");
        assertTrue(digitPattern.matcher(afterDash).matches());
    }
    private void assureEvensAreConsonantsOddsAreVowels(String before)
    {
        IntStream.range(0,before.length()).forEach((index)->{
            String string = (index %2 ==0) ?
                    consonants : vowels;
            if(0 == index) string = string.toUpperCase(Locale.getDefault());
            final String stringAt = String.valueOf(before.charAt(index));
            assertThat(string, containsString(stringAt));
        });
    }
}
