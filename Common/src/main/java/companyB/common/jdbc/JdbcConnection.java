package companyB.common.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Charles Burrell (deltafront@gmail.com).
 */
public class JdbcConnection
{
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcUrl;
    private JdbcUtils jdbcUtils;
    private String jdbcDriverClass;
    private final DataSource dataSource;

    public JdbcConnection(String jdbcUsername, String jdbcPassword, String jdbcDriverClass,
                          String jdbcUrl)
    {
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
        this.jdbcUrl = jdbcUrl;
        this.jdbcDriverClass = jdbcDriverClass;
        this.jdbcUtils = new JdbcUtils();
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);
        basicDataSource.setDriverClassName(jdbcDriverClass);
        basicDataSource.setUrl(jdbcUrl);
        this.dataSource = basicDataSource;
    }
    public JdbcConnection(DataSource dataSource)
    {
       this.dataSource = dataSource;

    }

    public Connection connection()
    {
        Connection connection = null;
        try
        {
            connection= dataSource.getConnection();
        }
        catch (SQLException e)
        {
            jdbcUtils.handleSqlException(e);
        }

        return connection;
    }
}
