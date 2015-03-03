# CompanyB_Modules

## Purpose
These modules represent reusable components that can be used in Java applications. These modules are designed to work with Spring, even though there is no hard-and-fast dependacny on that framework.

## Modules
The following modules are currently available:

* [**Common**](https://github.com/deltafront/CompanyB_Modules/tree/master/Common) - This module contains reusable components for consumption by other modules and applications.
* [**ConfigurationEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/ConfigurationEnabled) - This module allows several identically named properties to exist in the same property file delineated only by a contextual family.
* [**ContextEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/ContextEnabled)- This module is meant to provide the interface for (and default implantation of) a thread-safe application context that can be shared between classes running within a single application.
* [**Decortator**](https://github.com/deltafront/CompanyB_Modules/tree/master/Decorator) - This module provides one with the ability to decorate particular class fields directly using standard reflection.
* [**HttpCookieEnabled**](https://github.com/deltafront/CompanyB_Modules/tree/master/HttpCookieEnabled) - This module is designed to remove some of the boilerplate code around setting cookies that are required in order for a certain web application to function. 

Coming soon:
* [**HttpSessionEnabled**]() - This module is designed to remove some of the boilerplate code around setting session attributes that are required in order for a certain web application to function. 
* [**DataAccess**](https://github.com/deltafront/CompanyB_Modules/tree/master/DataAccess) - Provides a default interface and Hibernate-based implementation that will accommplish roughly 85% of all common data access actions.

## Contributing
### Become a contributor
If you would like to contribute directly to these modules, please Please email [deltafront@gmail.com](mailto:deltafront@gmail.com) in order to join the CompanyB Github organization.
### Logging issues
Please log all issues [here](https://github.com/deltafront/CompanyB_Modules/issues)
### Project tracking
Post 2.0 work will be tracked using [Trello](http://trello.com). You will need to be a member of the CompanyB Trello orginization in order to see the current status of all work. Please email [deltafront@gmail.com](mailto:deltafront@gmail.com) for access.
