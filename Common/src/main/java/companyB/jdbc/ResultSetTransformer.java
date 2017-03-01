package companyB.jdbc;

import java.sql.ResultSet;

/**
 *@author C.A. Burrell (deltafront@gmail.com)
 */
public interface ResultSetTransformer<TargetClass>
{
    TargetClass fromResultSet(ResultSet resultSet);
}
