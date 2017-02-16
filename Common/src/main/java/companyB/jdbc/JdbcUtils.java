package companyB.jdbc;

import companyB.common.utils.FactoryUtils;
import companyB.common.utils.FieldUtils;
import companyB.common.utils.UtilityBase;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *
 * @author C.A. Burrell deltafront@gmail.com
 * @since 3.0.0
 */
public class JdbcUtils extends UtilityBase
{
    private final FieldUtils fieldUtils;
    private final FactoryUtils factoryUtils;
    private final DataSource dataSource;
    private enum Operation
    {
        insert,update,query
    }


    /**
     * @param dataSource Datasource that will ultimately return a valid connection.
     * @since 3.0.0
     */
    public JdbcUtils(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.factoryUtils = new FactoryUtils();
        this.fieldUtils = new FieldUtils();
    }

    /**
     * @param jdbcUsername Username used to connect to a database.
     * @param jdbcPassword Password used by user to connect to database.
     * @param jdbcUrl URL of database.
     * @param jdbcDriverClass Fully Qualified name of JDBC driver class.
     * @since 3.0.0
     */
    public JdbcUtils(String jdbcUsername, String jdbcPassword, String jdbcUrl, String jdbcDriverClass)
    {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setDriverClassName(jdbcDriverClass);
        this.dataSource = basicDataSource;
        this.factoryUtils = new FactoryUtils();
        this.fieldUtils = new FieldUtils();
    }

    /**
     * Executes a SQL query against the database using a Prepared Statement.
     * @param sql SQL query to be executed.
     * @param resultSetTransformer Result Set Transformer used to bind the returned result set to POJOs.
     * @return A list containing all of the result mappings of the query.
     * @since 3.0.0
     */
    public<TargetClass> List<TargetClass>query(String sql, ResultSetTransformer<TargetClass> resultSetTransformer)
    {
        return query(sql,resultSetTransformer,new Object[0]);
    }
    /**
     * Executes a SQL query against the database using a Prepared Statement.
     * @param sql SQL query to be executed.
     * @param resultSetTransformer Result Set Transformer used to bind the returned result set to POJOs.
     * @param replacements List of replacement values to be used in the Prepared Statement.
     * @return A list containing all of the result mappings of the query.
     * @since 3.0.0
     */
    public<TargetClass> List<TargetClass>query(String sql, ResultSetTransformer<TargetClass> resultSetTransformer, Object...replacements)
    {
        final List<TargetClass>list =insertUpdateQuery(sql,Operation.query,resultSetTransformer,replacements);
        LOGGER.debug(String.format("Returning %s results from query.", list.size()));
        return list;
    }


    /**
     * Inserts data into the database using a simple Statement.
     * @param sql SQL query to be executed.
     * @return Id if inserted data, or -1 if insert was not successful.
     * @since 3.0.0
     */
    public Long insert(String sql)
    {
        return insert(sql,new Object[0]);
    }

    /**
     * Inserts data into the database using a Prepared Statement.
     * @param sql SQL query to be executed.
     * @param replacements List of replacement values to be used in the Prepared Statement.
     * @return Id if inserted data, or -1 if insert was not successful.
     * @since 3.0.0
     */
    public Long insert(String sql, Object...replacements)
    {
        Long id = insertUpdateQuery(sql,Operation.insert,null,replacements);
        LOGGER.trace(String.format("Returning id to client: %d", id));
        return id;
    }

    /**
     * Updates data in the database using a simple Statement.
     * @param sql SQL query to be executed.
     * @return Number of rows of data affected by update, or -1 if update was not successful.
     * @since 3.0.0
     */
    public Integer update(String sql)
    {
        return update(sql, new Object[0]);
    }
    /**
     * Updates data in the database using a simple Statement.
     * @param sql SQL query to be executed.
     * @param replacements List of replacement values to be used in the Prepared Statement.
     * @return Number of rows of data affected by update, or -1 if update was not successful.
     * @since 3.0.0
     */
    public Integer update(String sql, Object...replacements)
    {
        Integer updated = insertUpdateQuery(sql,Operation.update,null,replacements);
        LOGGER.debug(String.format("Number of rows updated: %d",updated));
        return updated;
    }

    /**
     * Executes Stored Procedure in Database.
     * @param call Formatted call to be executed.
     * @param callableParameters List of CallableParameters needed for the Stored Procedure.
     * @return  A list containing all of the result mappings of the query. The first element of this list
     *          will be any registered result parameters returned by the procedure.
     */
    public List<Map<String,Object>> executeCall(String call, CallableParameter...callableParameters)
    {
        List<Map<String,Object>>mappings = new LinkedList<>();
        try(Connection connection = dataSource.getConnection();
            CallableStatement callableStatement = connection.prepareCall(call))
        {
            final List<String>outs = new LinkedList<>();
            final Map<String,Object> mapping = new HashMap<>();
            IntStream.range(0,callableParameters.length).forEach(index ->
            {
                CallableParameter callableParameter = callableParameters[index];
                setCall(callableStatement, callableParameter, outs);
            });
            if(callableStatement.execute())
            {
                outs.forEach(out_parameter ->
                {
                    Object value = getOutValue(callableStatement, out_parameter);
                    mapping.put(out_parameter,value);
                });
                mappings.add(mapping);
                mappings.addAll(mappingsFromResultSet(callableStatement.getResultSet()));
            }
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
        LOGGER.debug(String.format("Returning %s mappings.", mappings.size()));
        return mappings;
    }


    private<TargetClass> List<TargetClass> fromResultSet(ResultSet resultSet, ResultSetTransformer<TargetClass>resultSetTransformer)
    {
        List<TargetClass>list = new LinkedList<>();
        try
        {
            while(resultSet.next())list.add(resultSetTransformer.fromResultSet(resultSet));
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
        LOGGER.debug("Returning {} elements.", list.size());
        return list;
    }

    private List<Map<String,Object>> mappingsFromResultSet(ResultSet resultSet)
    throws SQLException
    {
        final List<Map<String,Object>>mappings = new LinkedList<>();
        final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next())mappings.add(mapFromResultSet(resultSet,resultSetMetaData));
        LOGGER.trace(String.format("Returning %d mappings.", mappings.size()));
        return mappings;
    }

    private Map<String,Object>mapFromResultSet(ResultSet resultSet, ResultSetMetaData resultSetMetaData)
            throws SQLException
    {
        final Map<String,Object>mapping = new HashMap<>();IntStream.rangeClosed(1, resultSetMetaData.getColumnCount()).forEach(index ->
            getResultSetObject(resultSet, resultSetMetaData, mapping, index));
        LOGGER.trace(String.format("Returning mapping with %s elements.", mapping.size()));
        return mapping;
    }

    private void getResultSetObject(ResultSet resultSet, ResultSetMetaData resultSetMetaData, Map<String, Object> mapping, int index)
    {
        try
        {
            final String name = resultSetMetaData.getColumnName(index);
            final Object value = resultSet.getObject(name);
            LOGGER.debug(String.format("Adding mapping: %s=%s", name,value));
            mapping.put(name,value);
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
    }

    private Long getGeneratedKey(Statement statement) throws SQLException
    {
        Long key = -1L;
        final ResultSet rs = statement.getGeneratedKeys();
        if (rs.next())
        {
            Integer temp = (Integer)rs.getObject(1);
            if(temp > -1)key = Long.parseLong(String.valueOf(temp));
        }
        return key;
    }

    private Statement getStatement(String sql, Connection connection, Object[] replacements, boolean isInsert) throws SQLException
    {
        return (0 == replacements.length) ?
                statement(connection) :
                prepareStatement(connection,sql,isInsert);
    }
    private Statement statement(Connection connection) throws SQLException
    {
        return connection.createStatement();
    }
    private PreparedStatement prepareStatement(Connection connection, String sql, Boolean isInsert) throws SQLException
    {
        return (isInsert) ?
                connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) :
                connection.prepareStatement(sql);
    }
    private void doReplace(PreparedStatement statement, Object[] replacements)
    {
        IntStream.range(0, replacements.length).forEach(index ->
        {
            final Object value = replacements[index];
            replacePreparedStatement(statement,index+1,value);
        });
    }
    private void replacePreparedStatement(PreparedStatement statement, Integer index, Object value)
    {
        try
        {
            statement.setObject(index,value);
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
    }
    private Object getOutValue(CallableStatement callableStatement, String out_parameter)
    {
        Object value = null;
        try
        {
            value = callableStatement.getObject(out_parameter);
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
        LOGGER.debug(String.format("Returning value for out parameter '%s' : %s'.",out_parameter,value));
        return value;

    }

    private void  setCall(CallableStatement callableStatement, CallableParameter callableParameter,List<String>outs)
    {
        try
        {
            final CallableParameter.ParameterType parameterType = callableParameter.getParameterType();
            if(CallableParameter.ParameterType.IN.equals(parameterType))
                callableStatement.setObject(callableParameter.getName(), callableParameter.getValue());
            else
            {
                callableStatement.registerOutParameter(callableParameter.getName(), callableParameter.getSqlType());
                outs.add(callableParameter.getName());
            }
        }
        catch (SQLException e)
        {
            handleSqlException(e);
        }
    }
    @SuppressWarnings("unchecked")
    private <T, TargetClass> T insertUpdateQuery(String sql, Operation operation, ResultSetTransformer<TargetClass>resultSetTransformer, Object...replacements)
    {
        Object out = null;
        try(Connection connection = dataSource.getConnection();
            Statement statement = getStatement(sql, connection, replacements, false))

        {
            Boolean isPreparedStatement = statement instanceof PreparedStatement;
            if(isPreparedStatement)doReplace((PreparedStatement) statement, replacements);
            switch (operation)
            {
                case query:
                    ResultSet resultSet = (isPreparedStatement) ?
                            ((PreparedStatement)statement).executeQuery() :
                            statement.executeQuery(sql);
                    out = fromResultSet(resultSet,resultSetTransformer);
                    break;
                case update:
                    out = (isPreparedStatement) ?
                            ((PreparedStatement)statement).executeUpdate() :
                            statement.executeUpdate(sql);
                    break;
                case insert:
                    Boolean inserted = (isPreparedStatement) ?
                            ((PreparedStatement)statement).execute() :
                            statement.execute(sql);
                    out = getGeneratedKey(statement);
                    break;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error is " + e.getMessage());
            switch (operation)
            {
                case insert:
                    out = -1L;
                    break;
                case update:
                    out = -1;
                    break;
                case query:
                    out = new LinkedList<HashMap<String,Object>>();
                    break;
            }
            handleSqlException(e);
        }
        return (T)out;
    }
    private void handleSqlException(SQLException e)
    {
        final StringBuilder out = new StringBuilder(e.getMessage());
        e.forEach(ex -> out.append(String.format("\n%s", ex.getMessage())));
        out.trimToSize();
        e.printStackTrace();
        LOGGER.error(out.toString(),e);
    }

}
