package companyB.jdbc.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This helper returns the results of an INSERT Sql statement, specifically the number of rows inserted.
 * @author C.A. Burrell deltafront@gmail.com
 */
class InsertResultsHelper implements ResultHelper<Long>
{
    public Long returnResults(String sql, Statement statement, Boolean isPreparedStatement) throws SQLException
    {
        if(isPreparedStatement) ((PreparedStatement)statement).execute();
        else statement.execute(sql);
        return getGeneratedKey(statement);
    }

    @Override
    public Long returnErrorResult()
    {
        return -1L;
    }

    private Long getGeneratedKey(Statement statement) throws SQLException
    {
        final AtomicLong key = new AtomicLong(-1L);
        final ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) key.set(getLongKeyFromResultSet(rs));
        return key.get();
    }

    private Long getLongKeyFromResultSet(ResultSet rs) throws SQLException
    {
        final Integer temp = (Integer)rs.getObject(1);
        return temp > -1 ? Long.parseLong(String.valueOf(temp)) : -1L;
    }

}
