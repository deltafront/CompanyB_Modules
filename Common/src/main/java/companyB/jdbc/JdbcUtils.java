package companyB.jdbc;

import companyB.common.utils.UtilityBase;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class will handle CRUD DB operations using SQL queries.
 * For the time being, calling stored procedures is outside of the scope of this class.
 * @author C.A. Burrell deltafront@gmail.com
 * @since 2.3.0
 */
public class JdbcUtils extends UtilityBase
{
    private final DataSource dataSource;
    private final JdbcExceptionUtils jdbcExceptionUtils;
    private enum Operation
    {
        insert,update,query
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
        this.jdbcExceptionUtils = new JdbcExceptionUtils();
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
        return insertUpdateQuery(sql,Operation.update,null,replacements);
    }

    private<TargetClass> List<TargetClass> fromResultSet(ResultSet resultSet, ResultSetTransformer<TargetClass>resultSetTransformer) throws SQLException
    {
        List<TargetClass>list = new LinkedList<>();
        while(resultSet.next())list.add(resultSetTransformer.fromResultSet(resultSet));
        return list;
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

    private Statement getStatement(String sql, Connection connection, Object[] replacements) throws SQLException
    {
        return (0 == replacements.length) ?
                statement(connection) :
                prepareStatement(connection,sql);
    }
    private Statement statement(Connection connection) throws SQLException
    {
        return connection.createStatement();
    }
    private PreparedStatement prepareStatement(Connection connection, String sql) throws SQLException
    {
        return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
    }
    private void doReplace(PreparedStatement statement, Object[] replacements) throws SQLException
    {
        for(int i = 0; i < replacements.length; i++)
            statement.setObject(i +1,replacements[i]);
    }

    @SuppressWarnings("unchecked")
    private <T, TargetClass> T insertUpdateQuery(String sql, Operation operation, ResultSetTransformer<TargetClass>resultSetTransformer, Object...replacements)
    {
        Object out = null;
        try
        {
            Connection connection = dataSource.getConnection();
            Statement statement = getStatement(sql, connection, replacements);
            Boolean isPreparedStatement = statement instanceof PreparedStatement;
            if(isPreparedStatement)doReplace((PreparedStatement) statement, replacements);
            switch (operation)
            {
                case query:
                    ResultSet resultSet = isPreparedStatement ?
                            ((PreparedStatement)statement).executeQuery() :
                            statement.executeQuery(sql);
                    out = fromResultSet(resultSet,resultSetTransformer);
                    break;
                case update:
                    out = isPreparedStatement ?
                            ((PreparedStatement)statement).executeUpdate() :
                            statement.executeUpdate(sql);
                    break;
                case insert:
                    if(isPreparedStatement) ((PreparedStatement)statement).execute();
                    else statement.execute(sql);
                    out = getGeneratedKey(statement);
                    break;
            }
        }
        catch (SQLException e)
        {
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
            jdbcExceptionUtils.handleSqlException(e,LOGGER);
        }
        return (T)out;
    }

}
