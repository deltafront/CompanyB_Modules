package companyB.common.enums;


import java.util.Set;
import java.util.TreeSet;

import static companyB.common.utils.SimpleRegexUtils.isValidFormattedSeries;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * This enumeration contains utilities for Canadian Provinces.
 * It contains all of the provinces and territories as of April 2008.
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0
 */
public enum Canadian_Provinces
{
    Alberta("AB"),
    British_Columbia("BC"),
    Manitoba("MB"),
    New_Brunswick("NB"),
    Newfoundland_and_Labrador("NL"),
    Northwest_Territories("NT"),
    Nova_SCotia("NS"),
    Nanavut("NU"),
    Ontario("ON"),
    Prince_Edwards_Island("PE"),
    Quebec("QC"),
    Saskatchewan("SK"),
    Yukon("YT");
    public final String abbreviation;

    //default Constructor - accepts the abbreviation as its sole arg
    Canadian_Provinces(String abbr)
    {
        abbreviation = abbr;
    }

    /**
     * Checks to see whether the supplied input is a valid province (full province name or abbreviation)
     * @param province String to be evaluated.
     * @return true if the province is a valid one, or if the input is null || ""
     * @since 1.0
     */
    public static boolean isProvince(String province)
    {
        boolean isValid = false;
        if (!isEmpty(province))
        {
            province = province.toLowerCase().replace("_", " ");
            for (Canadian_Provinces canadian_province : Canadian_Provinces.values())
            {
                String full_name = canadian_province.name().toLowerCase().replace("_", " ");
                String abbreviation = canadian_province.abbreviation.toLowerCase();
                if (province.equals(full_name) || province.equals(abbreviation))
                {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * Returns an alphabetized Set of Canadian Provinces. The provinces themselves are presented "naturally",
     * without underscores standing in for spaces.
     * @param abbreviations set this to true if you just want the provincial abbreviations
     * @return Alphabetized list of provinces
     * @since 1.0
     */
    public static Set<String> displayProvinces(boolean abbreviations)
    {
        Set<String> provinces = new TreeSet<String>();
        for (Canadian_Provinces province : Canadian_Provinces.values())
        {
            String toadd = "";
            if (!abbreviations)
            {
                toadd = province.name().replace("_", " ");
            }
            else
            {
                toadd = province.abbreviation;
            }
            provinces.add(toadd);
        }
        return provinces;
    }

    /**
     * Determines whether the provided string represents a valid Canadian Postal Code. Valid inputs are
     * A1B-2D3 and A1B 2D3.
     * @param postal_code Postal code to be evaluated.
     * @return true if input represents a valid Canadian Postal code.
     * @since 1.0
     */
    public static boolean isValidCandianPostalCode(String postal_code)
    {
        return !isEmpty(postal_code) && isValidFormattedSeries("[A-Z]{1}[0-9]{1}[A-Z]{1}[-\\s]{1}[0-9]{1}[A-Z]{1}[0-9]{1}", postal_code);
    }
}