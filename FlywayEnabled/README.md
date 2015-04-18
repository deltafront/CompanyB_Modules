# FlywayEnabled

## Concept
This module provides a single point of contact for using the [Java Flyway DB migration](http://flywaydb.org/) framework.


## Usage
1.  Get a new instance of FlywayProperties:
    ```java
        String username = "sa";
        String password = "";
        String jdbcUrl = "jdbc:h2:file:./foobardb";
        String driverClass = "org.h2.Driver";
        String[] locations = new String[0];
        FlywayProperties flywayProperties = new FlywayProperties(username, password, jdbcUrl, driverClass, locations);
    ```
2.  Use this property to construct a new instance of FlywayMain:
    ```java
        FlywayMain flywayMain = new FlywayMain(flywayProperties);
    ```
3.  Perform these operations:
    ```java
        //migrate
        flywayMain.migrate();
        //Validate the migration
        flywayMain.validate();
        //Get the information related to the latest migration
        MigrationInfo[]infos = flywayMain.info();
        //If need be, roll back the entire migration
        flywayMain.clean();
    ```

## Logging
All logging is done via SLF4J. You will need to provide your own runtime implementations.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>FlywayEnabled</artifactId>
        <version>${flyway.enabled.version}</version>
    </dependency>
```
