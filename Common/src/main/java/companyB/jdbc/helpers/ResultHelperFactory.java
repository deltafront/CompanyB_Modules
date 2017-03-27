package companyB.jdbc.helpers;

import companyB.jdbc.ResultSetTransformer;
import companyB.jdbc.ResultSetTransformerDefaultImpl;

import java.util.Map;

/**
 * This factory is responsible for delivering
 * @author C.A. Burrell deltafront@gmail.com
 */
public class ResultHelperFactory
{

    /**
     * Returns a QueryResultHelper that uses the assigned ResultSetTransformer.
     * @param resultSetTransformer ResultSetTransformer to be used.
     * @param <T> Type of Object that is being transformed from the ResultSet.
     * @return QueryResultHelper that uses the supplied ReusultSetTransformer.
     */
    public <T>QueryResultHelper<T> queryResultsHelper(ResultSetTransformer<T> resultSetTransformer)
    {
        return new QueryResultHelper<>(resultSetTransformer);
    }

    /**
     * @return QueryResultHelper that uses the default ResultSetTransformer.
     */
    @SuppressWarnings("unchecked")
    public QueryResultHelper<Map<String,Object>> queryResultsHelper()
    {
        return new QueryResultHelper<>(new ResultSetTransformerDefaultImpl());
    }

    /**
     * @return InsertResultsHelper.
     */
    public InsertResultsHelper insertResultsHelper()
    {
        return new InsertResultsHelper();
    }

    /**
     * @return UpdateResultsHelper.
     */
    public UpdateResultsHelper updateResultsHelper()
    {
        return new UpdateResultsHelper();
    }
}
