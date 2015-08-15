# CompanyB_Modules

## Purpose
These modules represent reusable components that can be used in Java applications. These modules are designed to work with Spring, even though there is no hard-and-fast dependacny on that framework.

## Modules
The following modules are currently available:

* [**Common**](https://github.com/deltafront/CompanyB_Modules/tree/master/Common) - This module contains reusable components for consumption by other modules and applications.
* [**ConfigurationEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/ConfigurationEnabled) - This module allows several identically named properties to exist in the same property file delineated only by a contextual family.
* [**ContextEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/ContextEnabled)- This module is meant to provide the interface for (and default implantation of) a thread-safe application context that can be shared between classes running within a single application.
* [**Decorator**](https://github.com/deltafront/CompanyB_Modules/tree/master/Decorator) - This module provides one with the ability to decorate particular class fields directly using standard reflection.
* [**HttpCookieEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/HttpCookieEnabled) - This module is designed to remove some of the boilerplate code around setting cookies that are required in order for a certain web application to function.
* [**HttpSessionEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/HttpSessionEnabled) - This module is designed to remove some of the boilerplate code around setting session attributes that are required in order for a certain web application to function.
* [**HttpSiteEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/HttpSiteEnabled) - This module is designed to remove some of the boilerplate code around getting and setting various attributes and capabilities that are to be made available globally within the context of a web application.
* [**SpringEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/SpringEnabled) - By importing this module, you get a pre-built set of dependencies suitable for building most Spring-based applications. Post-processing annotations and default bean definitions are also available through this module.
* [**Flyway Enabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/FlywayEnabled) - This module provides a single point of contact for using the [Java Flyway DB migration](http://flywaydb.org/) framework.
* [**Event Logger**](https://github.com/deltafront/CompanyB_Modules/tree/master/EventLogger) - This module provides a single point of contact for logging specific events or even just plain messages in a unified format. This makes it possible to develop logging messages that can be written company-wide and easily indexed and searched by applications such as Splunk.

## Contributing
### Become a contributor
If you would like to contribute directly to these modules, please Please email [deltafront@gmail.com](mailto:deltafront@gmail.com) in order to join the CompanyB Github organization.
### Logging issues
Please log all issues [here](https://github.com/deltafront/CompanyB_Modules/issues)
### Project tracking
Post 2.0 work will be tracked using [Trello](http://trello.com). You will need to be a member of the CompanyB Trello organization in order to see the current status of all work. Please email [deltafront@gmail.com](mailto:deltafront@gmail.com) for access.

## PMD
By default, PMD is executed during the Maven build. We are using the basic ruleset found [here](http://pmd.sourceforge.net/pmd-4.3.0/rules/basic.html).

## Jacoco
Jacoco code coverage is executed during the builds. The following targets are supposed to be met:
* **Instruction** - 80%
* **Branch** - 80%

## Specifying test groups to execute
By default, the following test groups are executed during the build:
*   **Unit** - `unit`
*   **Integration** - `integration`
This behavior can be overridden by specifying the following via the command line:
*   **Unit** - -Dunit.test.groups=`{specify unit test groups here}`
*   **Integration** - -Dintegration.test.groups=`{specify integration test groups here}`

## Versioning
The following format will be used for versioning:
*Major*.*Minor*.*Point*
* **Major** - These releases will comprise new functionalities, modules and significant issue resolutions.
* **Minor** - These releases will comprise minor new features and bugfixes
* **Point** - These releases will comprise mainly documentation and dependency updates.
