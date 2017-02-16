package companyB.jdbc.test;

import org.h2.tools.SimpleResultSet;

import java.sql.Types;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class JdbcH2TestSprocs
{
    public static String allCaps(String string)
    {
        SimpleResultSet resultSet = new SimpleResultSet();
        resultSet.addColumn("result", Types.VARCHAR, 255, 0);
        resultSet.addRow(string.toUpperCase());
        return string.toUpperCase();
    }
}
