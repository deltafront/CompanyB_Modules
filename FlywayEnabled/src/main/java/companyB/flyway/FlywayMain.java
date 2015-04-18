package companyB.flyway;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.Validate;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class that acts as a container for a constructed instance of FlyWay.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version 1.0
 */
public class FlywayMain
{
    private final static Logger LOGGER = LoggerFactory.getLogger(FlywayMain.class);
    private final BasicDataSource dataSource;
    private final String[]locations;

    /**
     * Default Constructor.
     * @param flywayProperties FlywayProperties object containing all of the information needed to create a connection
     *                         to the underlying database.
     * @since 1.0
     */
    public FlywayMain(FlywayProperties flywayProperties)
    {
        Validate.notNull(flywayProperties,"Flyway Properties are required!");

        final String username = flywayProperties.getJdbcUserName();
        final String password = flywayProperties.getJdbcPassword();
        final String jdbcUrl = flywayProperties.getJdcbUrl();
        final String driverClassName  = flywayProperties.getDriverClassName();
        final String[]locations = flywayProperties.getLocations();

        Validate.notBlank(username,"JDBC Username is required.");
        Validate.notNull(password,"JDBC Password is required.");
        Validate.notBlank(jdbcUrl,"JDBC URL is required.");
        Validate.notBlank(driverClassName,"JDBC Connection Driver Class name is required.");

        this.dataSource = new BasicDataSource();
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
        this.dataSource.setUrl(jdbcUrl);
        this.dataSource.setDriverClassName(driverClassName);
        this.dataSource.setDefaultAutoCommit(true);
        this.locations = locations;

        LOGGER.trace(String.format("Init datasource using %s@%s [%s] (password redacted).",username,jdbcUrl,driverClassName));
    }

    /**
     * Performs the migration.
     * @return Number of scripts migrated.
     * @since 1.0
     */
    public int migrate()
    {
        return getFlyway().migrate();
    }

    /**
     * Cleans underlying DB schema.
     * @since 1.0
     */
    public void clean()
    {
        getFlyway().clean();
    }

    /**
     * Performs validation on the underlying schema.
     * @since 1.0
     */
    public void validate()
    {
        getFlyway().validate();
    }

    /**
     * @return All information regarding the current migration.
     * @since 1.0
     */
    public MigrationInfo[] info()
    {
        MigrationInfoService migrationInfoService = getFlyway().info();
        return migrationInfoService.all();
    }

    private Flyway getFlyway()
    {
        Validate.notNull(dataSource,"DataSource for JDBC Connections are required!");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        if(null != locations && 0!=locations.length) flyway.setLocations(locations);
        return flyway;
    }
}
