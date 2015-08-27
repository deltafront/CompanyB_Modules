package companyB.common.jdbc;

import companyB.common.utils.UtilityBase;

import java.sql.SQLException;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class JdbcUtils extends UtilityBase
{

    public void handleSqlException(SQLException sqle)
    {
        String message = getSqlExceptionString(sqle);
        LOGGER.error(message, sqle);
    }

    private String  getSqlExceptionString(SQLException sqle)
    {
        String message = String.format("%s [Error Code: %s\tSqlState: %s]\n",
                sqle.getMessage(), sqle.getErrorCode(), sqle.getSQLState());
        SQLException nextException = sqle.getNextException();
        if (null != nextException)message += getSqlExceptionString(nextException);
        return message;
    }
}
