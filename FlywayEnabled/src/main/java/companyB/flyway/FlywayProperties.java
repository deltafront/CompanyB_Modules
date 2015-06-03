package companyB.flyway;

import org.apache.commons.lang3.Validate;

/**
 * Container for FlyWay connection details.
 * @author Charles Burrell (deltafront@gmail.com)
 * @since 2.0.0
 */
public class FlywayProperties
{

    private final String jdbcUserName;
    private final String jdbcPassword;
    private final String jdcbUrl;
    private final String driverClassName;
    private final String[]locations;

    /**
     *
     * @param jdbcUserName - JDBC username. Can not be blank.
     * @param jdbcPassword - JDBC username. Can not be null (but can be blank).
     * @param jdcbUrl - JDBC URL. Can not be blank.
     * @param driverClassName - Fully qualified class name of the JDBC Connection Driver. Can not be blank.
     * @param locations - Locations of the migration scripts. If this is omitted or is empty, the default db.migration at db/db.migration will be used.
     * @since 2.0.0
     */
    public FlywayProperties(String jdbcUserName, String jdbcPassword, String jdcbUrl, String driverClassName, String[] locations)
    {
        Validate.notBlank(jdbcUserName,"JDBC Username is required.");
        Validate.notNull(jdbcPassword,"JDBC Password is required (can be an empty string).");
        Validate.notBlank(jdcbUrl,"JDBC URL is required.");
        Validate.notBlank(driverClassName, "Driver Class name is required.");
        this.jdbcUserName = jdbcUserName;
        this.jdbcPassword = jdbcPassword;
        this.jdcbUrl = jdcbUrl;
        this.driverClassName = driverClassName;
        this.locations = locations;
    }

    /**
     * @return JDBC username.
     * @since 2.0.0
     */
    public String getJdbcUserName()
    {
        return jdbcUserName;
    }

    /**
     * @return JDBC username.
     * @since 2.0.0
     */
    public String getJdbcPassword()
    {
        return jdbcPassword;
    }

    /**
     * @return JDBC URL.
     * @since 2.0.0
     */
    public String getJdcbUrl()
    {
        return jdcbUrl;
    }

    /**
     * @return Fully qualified class name of the JDBC Connection Driver.
     * @since 2.0.0
     */
    public String getDriverClassName()
    {
        return driverClassName;
    }

    /**
     * @return Locations of the migration scripts.
     * @since 2.0.0
     */
    public String[] getLocations()
    {
        return locations;
    }
}
