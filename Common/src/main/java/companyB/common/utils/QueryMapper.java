package companyB.common.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Produces a mapping of passed-in request query parameters.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.1.0
 */
public class QueryMapper extends UtilityBase
{
    class key_value
    {
        key_value(String kv)
        {
            if(kv.contains("="))
            {
                String[]split = kv.split("=");
                key = split[0];
                value = split[1];
            }
            else
            {
                key = kv;
                value = null;
            }
        }
        String key;
        String value;
    }

    /**
     * Produces a mapping of request queries. Each request query parameter is mapped to a list of values, since a single
     * request parameter can have more than a single value.
     * @param requestQuery request query to be mapped.
     * @return Mapping of request parameters.
     * @since 2.1.0
     */
    public Map<String,List<String>> mapRequestQuery(final String requestQuery)
    {
        String query = requestQuery;
        final Map<String,List<String>>mapping = new HashMap<>();
        if(query.contains("?"))query = query.replace("?","");
        while(query.contains("&&")) query = query.replace("&&","&");
        String[]keyValuePairs = getKeyValuePairs(query);
        for(final String keyValuePair : keyValuePairs)
        {
            key_value keyValue = getKeyValue(keyValuePair);
            final List<String>listing = getListing(keyValue.key,mapping);
            listing.add(keyValue.value);
            mapping.put(keyValue.key,listing);
            LOGGER.trace("Added value '{}' to listing for key '{}'. Listing has {} elements."
                    ,keyValue.value,keyValue.key,listing.size());
        }
        return mapping;
    }
    private String[]getKeyValuePairs(String requestQuery)
    {
        return requestQuery.split("&");
    }
    private key_value getKeyValue(String keyValuePair)
    {
        return new key_value(keyValuePair);
    }
    private List<String>getListing(String key, Map<String,List<String>>mapping)
    {
        if(mapping.containsKey(key)) return mapping.get(key);
        else
        {
            final List<String>listing = new LinkedList<>();
            mapping.put(key,listing);
            return getListing(key,mapping);
        }
    }
}