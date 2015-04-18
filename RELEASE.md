# 1.0.0 (2015-03-28)
*   Initial Release
# 1.1.0 (2015-04-10)
*   Switched from JUnit to TestNG
    *   [card](https://trello.com/c/quibF3jl/2-1-1-0-switch-to-testng-in-core-pom)
    *   [card](https://trello.com/c/OQ9m31GK/3-1-1-0-make-sure-that-all-test-classes-are-test-groups-unit)
*   Added SpringEnabled
    *   [card](https://trello.com/c/U1jDDfxd/29-1-1-1-switch-all-common-utility-classes-from-abstract-to-beans)
*   Switched out all public facing utility classes from abstract to concrete. There are no more static methods in these classes.
    *   [card](https://trello.com/c/U1jDDfxd/29-1-1-1-switch-all-common-utility-classes-from-abstract-to-beans)
*   Implemented ISO-8601 Simple Date Formatter
    *   [card](https://trello.com/c/kvi9st1N/1-1-1-0-implement-default-simpledateformatter-that-returns-date-string-in-iso-8601-format)
# 1.1.1
*   Fixed all failing tests
    *   [card](https://trello.com/c/VmbfZ7TH/22-1-1-1-fix-failing-tests)
*   Ability to deploy to Tomcat instance
    *   [card](https://trello.com/c/X0gubdWw/30-1-1-1-tomcat-7-maven-plugin)
*  Improved messaging for tests that have expected failure conditions
    *   [card](https://trello.com/c/FDK2TynQ/31-1-1-1-common-error-messages-in-validate-statements)