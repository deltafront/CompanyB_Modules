package companyB.jdbc.helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


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
