package companyB.jdbc;

import org.slf4j.Logger;

import java.sql.SQLException;

/**
 * Generic utility for handling JDBC exceptions.
 * @author C.A. Burrell deltafront@gmail.com
 */
class JdbcExceptionUtils
{
    /**
     * Logs the stack trace for the SQL exception. This method will append any embedded SQLExceptions to that of the original.
     * @param sqlException SQLException that has been caught.
     * @param logger Logger to which the output is to be written.
     */
    void handleSqlException(SQLException sqlException, Logger logger)
    {
        final StringBuilder out = new StringBuilder(sqlException.getMessage());
        sqlException.forEach(ex -> out.append(String.format("\n%s", ex.getMessage())));
        out.trimToSize();
        sqlException.printStackTrace();
        logger.error(out.toString(),sqlException);
    }
}
