package companyB.jdbc.helpers;

import companyB.jdbc.ResultSetTransformer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * This helper returns the results of an Query Sql statement, a list of transformed objects.
 * @author C.A. Burrell deltafront@gmail.com
 */
class QueryResultHelper<TargetClass>  implements ResultHelper<List<TargetClass>>
{
    private final ResultSetTransformer<TargetClass>resultSetTransformer;


    QueryResultHelper(ResultSetTransformer<TargetClass> resultSetTransformer)
    {
        this.resultSetTransformer = resultSetTransformer;
    }
    public List<TargetClass> returnResults(String sql,Statement statement, Boolean isPreparedStatement) throws SQLException
    {
        final ResultSet resultSet = isPreparedStatement ?
                ((PreparedStatement)statement).executeQuery() :
                statement.executeQuery(sql);
        return fromResultSet(resultSet,resultSetTransformer);
    }

    @Override
    public List<TargetClass> returnErrorResult()
    {
        return new LinkedList<>();
    }

    private List<TargetClass> fromResultSet(ResultSet resultSet, ResultSetTransformer<TargetClass>resultSetTransformer) throws SQLException
    {
        final List<TargetClass>list = new LinkedList<>();
        while(resultSet.next())list.add(resultSetTransformer.fromResultSet(resultSet));
        return list;
    }
}
