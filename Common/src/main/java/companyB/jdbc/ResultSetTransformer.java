package companyB.jdbc;

import java.sql.ResultSet;

/**
 * @author C.A. Burrell (deltafront@gmail.com)
 * @version 1.0.0
 */
public interface ResultSetTransformer<TargetClass>
{
    TargetClass fromResultSet(ResultSet resultSet);
}
