package companyB.jdbc;

import java.sql.ResultSet;

/**
 * Generic contract for transforming the contents of a result set to a specific object instance.
 * @author C.A. Burrell (deltafront@gmail.com)
 */
public interface ResultSetTransformer<TargetClass>
{
    /**
     * Transforms the contents of this result set into a specific target class.
     * @param resultSet Result set that contains the results of a SQL operation.
     * @return Instance of TargetClass.
     */
    TargetClass fromResultSet(ResultSet resultSet);
}
