package companyB.common.jdbc;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;


/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class JdbcParameter
{
    private final Object value;
    private final Integer index;
    private final JdbcUtils jdbcUtils;

    public JdbcParameter(Object value, Integer index)
    {
        Validate.notNull(value,"Value must be specified.");
        Validate.notNull(index,"Index must be provided.");
        Validate.isTrue(index > -1, "Index must be greater than -1.");
        this.value = value;
        this.index = index;
        jdbcUtils =  new JdbcUtils();
    }
    public void set(PreparedStatement statement)
    {
        Class classOf = value.getClass();
        try
        {
            if(classOf.equals(Long.class) || classOf.equals(long.class))
                statement.setLong(index, (Long)value);
            else if(classOf.equals(Short.class) || classOf.equals(short.class))
                statement.setShort(index,(Short)value);
            else if(classOf.equals(Float.class) || classOf.equals(float.class))
                statement.setFloat(index, (Float) value);
            else if(classOf.equals(Integer.class) || classOf.equals(int.class))
                statement.setInt(index, (Integer) value);
            else if(classOf.equals(Byte.class) || classOf.equals(byte.class))
                statement.setByte(index, (Byte) value);
            else if(classOf.equals(Boolean.class) || classOf.equals(boolean.class))
                statement.setBoolean(index, (Boolean) value);
            else if(classOf.equals(Double.class) || classOf.equals(double.class))
                statement.setDouble(index, (Double) value);
            else if(classOf.equals(String.class)
                    || classOf.equals(char.class)
                    || classOf.equals(Character.class))
                statement.setString(index, (String) value);
            else if(classOf.equals(Date.class))
                statement.setDate(index, (Date) value);
            else if(classOf.equals(Timestamp.class))
                statement.setTimestamp(index, (Timestamp) value);
            else if(classOf.equals(Time.class))
            statement.setTime(index, (Time) value);
            else if(classOf.equals(URL.class))
            statement.setURL(index, (URL) value);
            else if(classOf.equals(BigDecimal.class))
                statement.setBigDecimal(index, (BigDecimal) value);
        }
        catch (SQLException e)
        {
            jdbcUtils.handleSqlException(e);
        }
    }
}
