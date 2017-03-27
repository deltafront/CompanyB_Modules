package companyB.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Default implementation of ResultSetTransformer. This returns a Map of key->value pairs.
 * @author C.A. Burrell deltafront@gmail.com
 */
public class ResultSetTransformerDefaultImpl implements ResultSetTransformer<Map<String,Object>>
{
    private final JdbcExceptionUtils exceptionUtils = new JdbcExceptionUtils();
    private final Logger LOGGER = LoggerFactory.getLogger(ResultSetTransformerDefaultImpl.class);

    @Override
    public Map<String, Object> fromResultSet(ResultSet resultSet)
    {
       return getMappingsFromResultSet(resultSet);
    }

    private Map<String,Object> getMappingsFromResultSet(ResultSet resultSet)
    {
        final Map<String,Object>mapping = new HashMap<>();
        try
        {
            final ResultSetMetaData rdms = resultSet.getMetaData();
            IntStream.range(0,rdms.getColumnCount()).forEach((columnIndex)->{
                getSingleMappingFromResultSet(resultSet, mapping, rdms, columnIndex +1);
            });
        }
        catch (SQLException e)
        {
            exceptionUtils.handleSqlException(e,LOGGER);
        }
        return mapping;
    }

    private void getSingleMappingFromResultSet(ResultSet resultSet, Map<String, Object> mapping, ResultSetMetaData rdms, int columnIndex)
    {
        try
        {
            final String key = rdms.getColumnName(columnIndex);
            final Object value = resultSet.getObject(key);
            mapping.put(key,value);
            System.out.println(String.format("%s=%s", key, value));
        }
        catch (SQLException e)
        {
            exceptionUtils.handleSqlException(e,LOGGER);
        }

    }
}
