package companyB.jdbc.helpers;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Defines the contract required to allow the JDBC Utils methods to properly interpret and deliver the results from various
 * SQL Statements.
 * @param <T> Target type of the result expected.
 * @author C.A. Burrell deltafront@gmail.com
 */
public interface ResultHelper<T>
{
    /**
     * Returns the results from this SQL Statement
     * @param sql SQL Query to be executed
     * @param statement Statement to be executed
     * @param isPreparedStatement Whether or not this is a prepared statement.
     * @return Result of SQL statement exection.
     * @throws SQLException
     */
    T returnResults(String sql, Statement statement, Boolean isPreparedStatement) throws SQLException;

    T returnErrorResult();
}
