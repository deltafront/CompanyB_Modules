package companyB.jdbc;

import java.sql.ResultSet;

/**
 *@author C.A. Burrell (deltafront@gmail.com)
 */
@SuppressWarnings("PMD.UnusedModifier")
public interface ResultSetTransformer<TargetClass>
{
    public TargetClass fromResultSet(ResultSet resultSet);
}
