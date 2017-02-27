package companyB.scrambler;


import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * This class is mostly an experiment on my part. All it really does is takes a Long value that is based on a System Time
 * and convert it into a string using the following rules:
 * <ol>
 *     <li>The first letter of the string is capitalized;</li>
 *     <li>The letters in every even position is a consonant;</li>
 *     <li>The letters in every odd position is a vowel; and</li>
 *     <li>The final three or four digits of the Long value are appended to the end of the resultant string with a dash.</li>
 * </ol>
 * @author C.A. Burrell deltafront@gmail.com
 * @since 2.3.0
 */
public class Scrambler
{
    private Long seed;
    private final String vowels = "aeiouy";
    private final String consonants_1 = "bcdfghklm";
    private final String consonants_2 = "npqrstvwxz";
    private final Random random;

    public Scrambler()
    {
        this.seed = System.currentTimeMillis();
        this.random = new Random(seed);
    }

    /**
     * Resets the seed to the current System Time.
     */
    public void reset()
    {
        this.seed = System.currentTimeMillis();
        random.setSeed(seed);
    }

    /**
     * Returns the scrambled string to the user.
     * @return Scrambled String.
     */
    public String getScrambledString()
    {
        final List<Integer>seedInts = breakSeedIntoInts();
        final StringBuilder stringBuilder = new StringBuilder("");
        IntStream.range(0,seedInts.size()).forEach((index)->{
            final String strings = getStrings(index);
            String string = get(seedInts.get(index),strings);
            if(0 == index) string = string.toUpperCase(Locale.getDefault());
            stringBuilder.append(string);
        });
        stringBuilder.append("-");
        stringBuilder.append(getLastThreeOrFour());
        return stringBuilder.toString();
    }
    private String getLastThreeOrFour()
    {
        final String seedString = String.valueOf(seed);
        final Integer upper = (random.nextInt() % 2 == 0) ?
                4 : 3;
        final Integer start = seedString.length() - upper;
        return seedString.substring(start);
    }
    private List<Integer> breakSeedIntoInts()
    {
        final List<Integer>integers = new LinkedList<>();
        final String seedString = String.valueOf(seed);
        IntStream.range(0,seedString.length()).forEach((index)->{
            integers.add((int) seedString.charAt(index));
        });
        return integers;
    }
    private String getStrings(Integer nextInt)
    {
        return (nextInt % 2 == 0) ?
                chooseConsonantList() : vowels;
    }
    private String chooseConsonantList()
    {
        return (random.nextInt() % 2 ==0) ?
                consonants_1 : consonants_2;
    }
    private String get(Integer position, String strings)
    {
        final Integer upper = strings.length() -1;
        final Integer index = (position > upper) ?
                getNumberWithinRange(upper) :
                upper;
        return String.valueOf(strings.charAt(index));

    }
    private Integer getNumberWithinRange(Integer upper)
    {
        return random.nextInt(upper);
    }
}
