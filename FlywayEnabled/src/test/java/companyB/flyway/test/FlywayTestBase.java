package companyB.flyway.test;

public class FlywayTestBase
{
    protected String username = "sa";
    protected String password = "";
    protected String jdbcUrl = "jdbc:h2:file:./foobardb";
    protected String driverClass = "org.h2.Driver";
    protected String []locations = {"filesystem:/db.migration/v1","filesystem:/db.migration/v2"};
}
