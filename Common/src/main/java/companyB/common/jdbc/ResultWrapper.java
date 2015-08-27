package companyB.common.jdbc;

import org.apache.commons.lang3.Validate;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class ResultWrapper implements Iterable<Map<String,Object>>
{
    private final List<Map<String,Object>> mappings;

    public ResultWrapper(ResultSet resultSet)
    {
        Validate.notNull(resultSet,"Result Set is required!");
        mappings = new JdbcResultSetUtils().getResultSetListing(resultSet);
    }

    public List<Object>getSingleColumnValues(String columnName)
    {
        List<Object>values = new LinkedList<>();
        for(Map<String,Object>mapping : mappings)values.add(mapping.get(columnName));
        return values;
    }

    @Override
    public Iterator<Map<String, Object>> iterator()
    {
        return mappings.iterator();
    }
}
