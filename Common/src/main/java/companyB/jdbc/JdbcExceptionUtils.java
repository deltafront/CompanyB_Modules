package companyB.jdbc;

import org.slf4j.Logger;

import java.sql.SQLException;

/**
 * Created by chburrell on 2/21/17.
 */
public class JdbcExceptionUtils
{
    public void handleSqlException(SQLException sqlException, Logger logger)
    {
        final StringBuilder out = new StringBuilder(sqlException.getMessage());
        sqlException.forEach(ex -> out.append(String.format("\n%s", ex.getMessage())));
        out.trimToSize();
        sqlException.printStackTrace();
        logger.error(out.toString(),sqlException);
    }
}
