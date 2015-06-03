package companyB.flyway.test;

import companyB.flyway.FlywayMain;
import companyB.flyway.FlywayProperties;
import companyB.flyway.FlywayUtils;
import org.flywaydb.core.api.MigrationInfo;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Test(groups = {"unit","flyway","flyway.enabled"})
public class FlywayTest extends FlywayTestBase
{
    private FlywayMain flywayMain;

    @BeforeMethod
    public void before()
    {
        username = "sa";
        password = "";
        jdbcUrl = "jdbc:h2:file:./foobardb";
        driverClass = "org.h2.Driver";
        locations = new String[0];
        FlywayProperties flywayProperties = new FlywayProperties(username, password, jdbcUrl, driverClass, locations);
        flywayMain = new FlywayMain(flywayProperties);
    }

    public void testSetup()
    {
        try
        {
            int init = flywayMain.migrate();
            assertTrue(init > 0);
            flywayMain.validate();
            init = flywayMain.migrate();
            assertEquals(0,init);
            Class.forName("org.h2.Driver");
            Connection connection= DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from PERSON");
            int rows = 0;
            while(resultSet.next())
            {
                rows ++;
            }
            assertEquals(3,rows);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            flywayMain.clean();
        }
    }

    public void testInfo()
    {
        try
        {
            int init = flywayMain.migrate();
            assertTrue(init > 0);
            MigrationInfo[] infos = flywayMain.info();
            assertNotNull(infos);
            assertTrue(infos.length > 0);
            FlywayUtils utils  = new FlywayUtils();
            String info = utils.migrationInfoToString(infos);
            assertNotNull(info);
            System.out.println(info);
        }
        finally
        {
            flywayMain.clean();
        }
    }
}
