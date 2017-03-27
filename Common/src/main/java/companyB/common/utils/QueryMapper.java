package companyB.common.utils;

import java.util.*;

/**
 * Produces a mapping of passed-in request query parameters.
 * @author Charles Burrell (deltafront@gmail.com)
 */
public class QueryMapper extends UtilityBase
{
    private class key_value
    {
        key_value(String kv)
        {
            if(kv.contains("="))getKeyValueFromDelimitedString(kv);
            else getKeyValueFromNonDelimitedString(kv);
        }

        private void getKeyValueFromNonDelimitedString(String kv)
        {
            key = kv;
            value = null;
        }

        private void getKeyValueFromDelimitedString(String kv)
        {
            String[]split = kv.split("=");
            key = split[0];
            value = split[1];
        }

        String key;
        String value;
    }

    /**
     * Produces a mapping of request queries. Each request query parameter is mapped to a list of values, since a single
     * request parameter can have more than a single value.
     * @param requestQuery request query to be mapped.
     * @return Mapping of request parameters.
     */
    public Map<String,List<String>> mapRequestQuery(String requestQuery)
    {
        final String query = cleanQuery(requestQuery);
        final Map<String,List<String>>mapping = new HashMap<>();
        getKeyValuePairs(query).forEach((keyValuePair)-> mapQuery(mapping, keyValuePair));
        return mapping;
    }

    private String cleanQuery(String query)
    {
        String _q = query;
        if(_q.contains("?"))_q = _q.replace("?","");
        while(_q.contains("&&")) _q = _q.replace("&&","&");
        return _q;
    }

    private void mapQuery(Map<String, List<String>> mapping, String keyValuePair)
    {
        key_value keyValue = getKeyValue(keyValuePair);
        final List<String>listing = getListing(keyValue.key,mapping);
        listing.add(keyValue.value);
        mapping.put(keyValue.key,listing);
    }

    private List<String>getKeyValuePairs(String requestQuery)
    {
        return Arrays.asList(requestQuery.split("&"));
    }
    private key_value getKeyValue(String keyValuePair)
    {
        return new key_value(keyValuePair);
    }
    private List<String>getListing(String key, Map<String,List<String>>mapping)
    {
        return mapping.containsKey(key) ? mapping.get(key) : getNewListingMapping(key,mapping);
    }

    private List<String> getNewListingMapping(String key, Map<String, List<String>> mapping)
    {
        mapping.put(key,new LinkedList<>());
        return getListing(key,mapping);
    }
}