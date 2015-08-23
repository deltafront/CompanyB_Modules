package companyB.http.requestQuery;

import companyB.cache.ExternalCache;
import companyB.common.utils.QueryMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Processes query parameters, and returns values from either the request parameter itself, or an external cache, which
 * can hold query parameters that may be too large for HTTP standard.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.20
 */
public class HttpRequestQueryProcessor
{
    private final static Logger LOGGER  = LoggerFactory.getLogger(HttpRequestQueryProcessor.class);
    private final ExternalCache<String,String> externalCache;
    private final String requestMappingKey;
    private final QueryMapper queryMapper;


    /**
     * Default constructor. Sets a null ExternalCache implementation.
     */
    public HttpRequestQueryProcessor()
    {
        this(null,null);
    }

    /**
     * @param externalCache ExternalCache implementation to be used.
     * @param requestMappingKey Request parameter that is used to get the value that serves as a key for the cache lookup.
     *                          If the ExternalCache is not null, this value must be non-null and not-empty.
     */
    public HttpRequestQueryProcessor(ExternalCache<String,String>externalCache, String requestMappingKey)
    {
        this.externalCache = externalCache;
        if(null != externalCache) Validate.notBlank(requestMappingKey,"Request mapping key for cache lookup must be provided.");
        this.requestMappingKey = requestMappingKey;
        this.queryMapper = new QueryMapper();
    }

    /**
     * @param request HttpServletRequest to get query parameter(s) from.
     * @param requestParameter Request parameter to get value from.
     * @return Value associated with request parameter.
     *  - If there are none, then a null string will be returned.
     *  - If there are several, then only the first value will be returned.
     * @since 2.2.0
     */
    public String getRequestQueryParameter(HttpServletRequest request,String requestParameter)
    {
        String[]parameterValues = getRequestQueryParameters(request,requestParameter);
        String value = (0 == parameterValues.length) ? null : parameterValues[0];
        LOGGER.trace(String.format("Returning value '%s' for request parameter '%s'.",value,requestParameter));
        return value;
    }

    /**
     * @param request HttpServletRequest to get query parameter(s) from.
     * @param requestParameter Request parameter to get value from.
     * @return All values associated with this request parameter. If there are none, an empty array will be returned.
     */
    public String[]getRequestQueryParameters(HttpServletRequest request, String requestParameter)
    {
        Map<String, List<String>> mapping = getRequestQueryMapping(request);
        List<String>parameterValues = mapping.get(requestParameter);
        if(null != parameterValues)
        {
            LOGGER.trace(String.format("Returning %d values for request parameter %s.",
                    parameterValues.size(),requestParameter));
            return parameterValues.toArray(new String[parameterValues.size()]);
        }
        else
        {
            LOGGER.trace(String.format("No parameter values found for request parameter %s.",requestParameter));
            return new String[0];
        }


    }

    /**
     * @param request HttpServletRequest to get query parameter(s) from.
     * @return Mapping of request values, keyed to individual parameters.
     * @since 2.2.0
     */
    public Map<String,List<String>> getRequestQueryMapping(HttpServletRequest request)
    {
        return (null != externalCache) ? getMappingFromCache(request) : getMappingFromRequestString(request);
    }

    private Map<String, List<String>> getMappingFromRequestString(HttpServletRequest request)
    {
        Map<String, List<String>> mapping;
        String queryString = request.getQueryString();
        mapping = queryMapper.mapRequestQuery(queryString);
        LOGGER.trace(String.format("Returned '%d' mappings for query string '%s'.",
                mapping.size(), queryString));
        return mapping;
    }

    private Map<String, List<String>> getMappingFromCache(HttpServletRequest request)
    {
        Map<String, List<String>> mapping;
        String mappingKey = request.getParameter(requestMappingKey);
        System.out.println("Mapping key = " + mappingKey);
        String queryFromCache = externalCache.remove(mappingKey);
        mapping = queryMapper.mapRequestQuery(queryFromCache);
        LOGGER.trace(String.format("Returned '%d' mappings for query string '%s' (keyed to '%s=%s')",
                mapping.size(),queryFromCache,requestMappingKey,mappingKey));
        return mapping;
    }

}
