package companyB.jdbc.helpers;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by chburrell on 3/22/17.
 */
public interface ResultHelper<T>
{
    T returnResults(String sql, Statement statement, Boolean isPreparedStatement) throws SQLException;

    T returnErrorResult();
}
