package companyB.jdbc.helpers;

import companyB.jdbc.ResultSetTransformer;

public class ResultHelperFactory
{

    public <T>QueryResultHelper<T> queryResultsHelper(ResultSetTransformer<T> resultSetTransformer)
    {
        return new QueryResultHelper<>(resultSetTransformer);
    }
    public InsertResultsHelper insertResultsHelper()
    {
        return new InsertResultsHelper();
    }
    public UpdateResultsHelper updateResultsHelper()
    {
        return new UpdateResultsHelper();
    }


}
