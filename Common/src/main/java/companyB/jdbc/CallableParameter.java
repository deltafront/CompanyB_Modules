package companyB.jdbc;

/**
 * This class represents a single value that is either passed to or obtained from a Stored Procedure.
 *
 * @author C.A. Burrell (deltafront@gmail.com)
 * @since 3.0.0
 */
public class CallableParameter
{
    public enum ParameterType
    {
        IN,OUT
    }
    private final String name;
    private final Integer sqlType;
    private final Object value;
    private final ParameterType parameterType;

    /**
     * Constructor for use with stored procedure output parameters.
     * @param name Name of stored procedure parameter.
     * @param sqlType The JDBC type code defined by <code>java.sql.Types</code>.
     */
    public CallableParameter(String name, Integer sqlType)
    {
        this(name,sqlType,null,ParameterType.OUT);
    }

    /**
     * Constructor for use with stored procedure input parameters.
     * @param name Name of stored procedure parameter.
     * @param value The value of the stored procedure parameter.
     */
    public CallableParameter(String name, Object value)
    {
        this(name,null,value,ParameterType.IN);
    }

    private CallableParameter(String name, Integer sqlType, Object value, ParameterType parameterType)
    {
        this.name = name;
        this.sqlType = sqlType;
        this.value = value;
        this.parameterType = parameterType;
    }

    public String getName()
    {
        return name;
    }


    public Integer getSqlType()
    {
        return sqlType;
    }

    public Object getValue()
    {
        return value;
    }

    public ParameterType getParameterType()
    {
        return parameterType;
    }
}
