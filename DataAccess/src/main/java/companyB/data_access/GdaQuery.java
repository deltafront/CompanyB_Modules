package companyB.data_access;

public class GdaQuery
{
    public enum Operator
    {
        eq,gt,gte,lt,lte
    }
    public String field;
    public Object value;
    public Operator operator = Operator.eq;

}
