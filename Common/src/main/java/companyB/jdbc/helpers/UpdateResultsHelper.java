package companyB.jdbc.helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This helper returns the results of an UPDATE Sql statement, specifically the number of rows updated.
 * @author C.A. Burrell deltafront@gmail.com
 */
class UpdateResultsHelper implements ResultHelper<Integer>
{

    public Integer returnResults(String sql, Statement statement, Boolean isPreparedStatement) throws SQLException
    {
        return isPreparedStatement ?
                ((PreparedStatement)statement).executeUpdate() :
                statement.executeUpdate(sql);
    }

    @Override
    public Integer returnErrorResult()
    {
        return -1;
    }
}
