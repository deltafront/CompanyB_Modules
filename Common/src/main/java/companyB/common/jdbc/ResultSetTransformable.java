package companyB.common.jdbc;

import java.sql.ResultSet;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public interface ResultSetTransformable<TransformedClass>
{
    public <TransformedClass>TransformedClass fromResultSet(ResultSet resultSet);
}
