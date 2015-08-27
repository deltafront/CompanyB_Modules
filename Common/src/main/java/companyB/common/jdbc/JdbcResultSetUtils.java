package companyB.common.jdbc;

import companyB.common.utils.UtilityBase;
import org.apache.commons.lang3.Validate;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class JdbcResultSetUtils extends UtilityBase
{
    private final static JdbcUtils JDBC_UTILS = new JdbcUtils();
    public <T extends ResultSetTransformable>List<T>listFromResultSet(ResultSet resultSet, Class<T>c)
    {
        List<T>result = new LinkedList<>();
        try
        {
            while (resultSet.next())result.add(fromResultSet(resultSet,c));
        }
        catch (SQLException e)
        {
            JDBC_UTILS.handleSqlException(e);
        }
        LOGGER.trace(String.format("Returning %d results.", result.size()));
        return result;
    }
    @SuppressWarnings("unchecked")
    public <T extends ResultSetTransformable>T fromResultSet(ResultSet resultSet, Class<T>c)
    {
        T out = null;
        try
        {
            out = (T)c.newInstance().fromResultSet(resultSet);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
           LOGGER.error(e.getMessage(),e);
        }

        if(null != out)LOGGER.trace(String.format("Returning instance of %s:\n%s",
                out.getClass(), GSON.toJson(out)));
        Validate.notNull(out,"Transformation from result set failed.");
        return out;
    }


    public List<Map<String, Object>>getResultSetListing(ResultSet resultSet)
    {
        List<Map<String, Object>> listings = new LinkedList<>();
        try
        {
            while(resultSet.next())
                listings.add(getResultSetMapping(resultSet));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        LOGGER.trace(String.format("Returning %d listings.", listings.size()));
        return listings;
    }

    public Map<String,Object>getResultSetMapping(ResultSet resultSet)
    {
        Map<String,Object>mapping  = new HashMap<>();
        try
        {
            ResultSetMetaData resultSetMetaData = resultSetMetaData(resultSet);
            List<String>columnNames = getColumnNames(resultSetMetaData);
            for(String columnName : columnNames)
                mapping.put(columnName, resultSet.getObject(columnName));
        }
        catch (SQLException e)
        {
            JDBC_UTILS.handleSqlException(e);
        }

        LOGGER.trace(String.format("Returning result set row:\n %s",
                GSON.toJson(mapping)));
        return mapping;
    }
    private ResultSetMetaData resultSetMetaData(ResultSet resultSet) throws SQLException
    {
        ResultSetMetaData  resultSetMetaData = resultSet.getMetaData();
        Validate.notNull( resultSetMetaData,"Result Set Metadata is null!");
        return resultSetMetaData;
    }
    private List<String> getColumnNames(ResultSetMetaData resultSetMetaData) throws SQLException
    {
        List<String>names  = new LinkedList<>();
        int count = resultSetMetaData.getColumnCount();
        for(int i = 1; i <= count; i++)
            names.add(resultSetMetaData.getColumnName(i));
        LOGGER.trace(String.format("Returning row names: %s", GSON.toJson(names)));
        return names;
    }




}
