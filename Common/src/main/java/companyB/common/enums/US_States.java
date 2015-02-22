package companyB.common.enums;

import java.util.Set;
import java.util.TreeSet;

import static companyB.common.utils.SimpleRegexUtils.isValidFormattedSeries;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * This class contains all of the US States and Territories as of April 2008.
 *
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0
 */
public enum US_States
{
    California("CA"),
    Texas("TX"),
    New_York("NY"),
    Florida("FL"),
    Illinios("IL"),
    Pennsylvania("PA"),
    Ohio("OH"),
    Michagan("MI"),
    Federated_States_of_Micronesia("FM"),
    Georgia("GA"),
    New_Jersey("NJ"),
    North_Carolina("NC"),
    Virginia("VA"),
    Marshall_Islands("MH"),
    Massachusetts("MA"),
    Washington("WA"),
    Indiana("IN"),
    Tennessee("TN"),
    Arizona("AZ"),
    Montana("MO"),
    Maryland("MD"),
    Wisconsin("WI"),
    Minnesota("MN"),
    Colorado("CO"),
    Alabama("AL"),
    Louisiana("LA"),
    South_Carolina("SC"),
    Kentucky("KY"),
    Puerto_Rico("PR"),
    Oregon("OR"),
    Oklahoma("OK"),
    Conneticut("CN"),
    Iowa("IA"),
    Missouri("MS"),
    Mississippi("MI"),
    Arkansas("AR"),
    Utah("UT"),
    Kansas("KA"),
    Nevada("NV"),
    New_Mexico("NM"),
    West_Virginia("WV"),
    Nebraska("NE"),
    Idaho("ID"),
    American_Samoa("AS"),
    Northern_Mariana_Islands("MP"),
    Maine("ME"),
    New_Hampshire("NH"),
    Hawaii("HA"),
    Rhode_Island("RH"),
    Delaware("DE"),
    South_Dakota("SD"),
    North_Dakota("ND"),
    Vermont("VT"),
    District_of_Columbia("DC"),
    Wyoming("WY"),
    Alaska("AK"),
    Guam("GU"),
    United_States_Virgin_Islands("VI");

    public final String abbreviation;

    //default constructor - accepts the abbreviation as its sole arg
    US_States(String abbr)
    {
        abbreviation = abbr;
    }

    /**
     * Checks to see whether the supplied input is a valid state (full state name or abbreviation)
     *
     * @param state string to be evaluated.
     * @return true if the state is a valid one, or if the input is null || ""
     * @since 1.0
     */
    public static boolean isState(String state)
    {
        boolean isValid = false;
        if (!isEmpty(state))
        {
            state = state.toLowerCase().replace("_", " ");
            for (US_States us_state : US_States.values())
            {
                String full_name = us_state.name().toLowerCase().replace("_", " ");
                String abbreviation = us_state.abbreviation.toLowerCase();
                if (state.equals(full_name) || state.equals(abbreviation))
                {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * Returns an alphabetized Set of U.S. States. The states themselves are presented "naturally",
     * without underscores standing in for spaces.
     *
     * @param abbreviations set this to true if you just want the state abbreviations
     * @return Alphabetized list of states
     * @since 1.0
     */
    public static Set<String> displayStates(boolean abbreviations)
    {
        Set<String> states = new TreeSet<String>();
        for (US_States state : US_States.values())
        {
            String toadd = "";
            if (!abbreviations)
            {
                toadd = state.name().replace("_", " ");
            }
            else
            {
                toadd = state.abbreviation;
            }
            states.add(toadd);
        }
        return states;
    }

    /**
     * Convenience method for determining whether the submitted postal code is a valid U.S one.
     * Valid formats are 12345, 12345-6789, or 12345 6789.
     *
     * @param postal_code postal code to be evaluated.
     * @return true if this represents a valid US Postal Code.
     * @since 1.0
     */
    public static boolean isValidUSZip(String postal_code)
    {
        return !isEmpty(postal_code) && (isValidFormattedSeries("[0-9]{5}", postal_code)
                || isValidFormattedSeries("[0-9]{5}\\-[0-9]{4}", postal_code)
                || isValidFormattedSeries("[0-9]{5}\\ [0-9]{4}", postal_code));
    }
}