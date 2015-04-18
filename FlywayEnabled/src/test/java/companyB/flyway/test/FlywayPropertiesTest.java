package companyB.flyway.test;

import companyB.flyway.FlywayProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

@SuppressWarnings("ConstantConditions")
@Test(groups = {"unit","flyway","flyway.properties"})
public class FlywayPropertiesTest extends FlywayTestBase
{
    private FlywayProperties flywayProperties;
    @BeforeMethod
    public void before()
    {
        username = "sa";
        password = "password";
        jdbcUrl = "jdbc:h2:file:./foobardb";
        driverClass = "org.h2.Driver";
        locations = new String[]{"filesystem:./db.migration/v1","filesystem:./db.migration/v2"};
    }
    public void testHappyPath()
    {
        flywayProperties = new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        validateFlywayProperties();
    }
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullUsername()
    {
        username = null;
        new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        fail("Null Pointer Exception expected - null username.");
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyStringUsername()
    {
        username = "";
        new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        fail("Illegal Argument Exception expected - empty string username.");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullPassword()
    {
        password = null;
        new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        fail("Null Pointer Exception expected - null password.");
    }
    public void testEmptyStringPassword()
    {
        password = "";
        flywayProperties = new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        validateFlywayProperties();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullJdbcUrl()
    {
        jdbcUrl = null;
        new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        fail("Null Pointer Exception expected - null jdbc url.");
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyStringJdbc()
    {
        jdbcUrl = "";
        new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        fail("Illegal Argument Exception expected - empty string jdbc url.");
    }

    public void testNullLocations()
    {
        locations = null;
        flywayProperties = new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        validateFlywayProperties();
    }
    public void testEmptyLocations()
    {
        locations = new String[0];
        flywayProperties = new FlywayProperties(username,password,jdbcUrl,driverClass,locations);
        validateFlywayProperties();
    }

    private void validateFlywayProperties()
    {
        assertNotNull(flywayProperties);
        assertEquals(username,flywayProperties.getJdbcUserName());
        assertEquals(password,flywayProperties.getJdbcPassword());
        assertEquals(driverClass,flywayProperties.getDriverClassName());
        assertEquals(jdbcUrl,flywayProperties.getJdcbUrl());
        String[] locationsFromProperties = flywayProperties.getLocations();
        if(null == locations) assertNull(locationsFromProperties);
        else arraysContainEqualValues(locationsFromProperties,locations);
    }
    private void arraysContainEqualValues(String[]array1, String[]array2)
    {
        Assert.assertEquals(array1.length,array2.length);
        for(String value : array2)
        {
            assertTrue(ArrayUtils.contains(array1,value));
        }
    }
}
