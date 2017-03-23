package companyB.jdbc;

import companyB.common.utils.UtilityBase;
import companyB.jdbc.helpers.ResultHelper;
import companyB.jdbc.helpers.ResultHelperFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class will handle CRUD DB operations using SQL queries.
 * For the time being, calling stored procedures is outside of the scope of this class.
 * @author C.A. Burrell deltafront@gmail.com
 * @version 1.0.0
 */
public class JdbcUtils extends UtilityBase
{
    private final DataSource dataSource;
    private final JdbcExceptionUtils jdbcExceptionUtils;
    private final ResultHelperFactory resultHelperFactory;
    private enum Operation
    {
        insert,update,query
    }


    /**
     * @param jdbcUsername Username used to connect to a database.
     * @param jdbcPassword Password used by user to connect to database.
     * @param jdbcUrl URL of database.
     * @param jdbcDriverClass Fully Qualified name of JDBC driver class.
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
        this.resultHelperFactory = new ResultHelperFactory();
    }

    /**
     * Executes a SQL query against the database using a Prepared Statement.
     * @param sql SQL query to be executed.
     * @param resultSetTransformer Result Set Transformer used to bind the returned result set to POJOs.
     * @return A list containing all of the result mappings of the query.
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
     */
    public<TargetClass> List<TargetClass>query(String sql, ResultSetTransformer<TargetClass> resultSetTransformer, Object...replacements)
    {
        return insertUpdateQuery(sql,resultHelperFactory.queryResultsHelper(resultSetTransformer),replacements);
    }


    /**
     * Inserts data into the database using a simple Statement.
     * @param sql SQL query to be executed.
     * @return Id if inserted data, or -1 if insert was not successful.
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
     */
    public Long insert(String sql, Object...replacements)
    {
        return insertUpdateQuery(sql,resultHelperFactory.insertResultsHelper(),replacements);
    }

    /**
     * Updates data in the database using a simple Statement.
     * @param sql SQL query to be executed.
     * @return Number of rows of data affected by update, or -1 if update was not successful.
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
     */
    public Integer update(String sql, Object...replacements)
    {
        return insertUpdateQuery(sql,resultHelperFactory.updateResultsHelper(),replacements);
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
    private <T, TargetClass> T insertUpdateQuery(String sql, ResultHelper resultHelper,Object...replacements)
    {
        Object out = null;
        try(final Connection connection = dataSource.getConnection();
            final Statement statement = getStatement(sql, connection, replacements);)
        {
            Boolean isPreparedStatement = statement instanceof PreparedStatement;
            if(isPreparedStatement)doReplace((PreparedStatement) statement, replacements);
            out = resultHelper.returnResults(sql,statement,isPreparedStatement);
        }
        catch (SQLException e)
        {
            out = resultHelper.returnErrorResult();
            jdbcExceptionUtils.handleSqlException(e,LOGGER);
        }
        return (T)out;
    }

}
