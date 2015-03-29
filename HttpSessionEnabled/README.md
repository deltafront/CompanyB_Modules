# HttpSessionEnabled

## Concept
This module allows you to define and access attributes that should be present in every session.

## Usage
1. Create a Session Definition file.The file must contain one line adhering to the following:
  * If maxInterval is to be defined:
  ```
    42=foo,bar,one,two
  ```
  * If maxInterval is not to be defined:
  ```
    foo,bar,one,two
   ```
2. Read this file:
```java
    DefaultSessionAttributes defaultSessionAttributes = DefaultSessionAttributesReader.readDefaultSessionAttributes("foo.properties");
```
3. Use the utils to set and get these attributes:

```java
    DefaultSessionUtils utils = new DefaultSessionUtils(defaultSessionAttributes);
    utils.setDefaultSessionAttribute(request, "foo",42,true,true);
    Object value = utils.getDefaultSessionAttribute(request,"foo",false);
```
## Logging
SLF4J is being used as a facade for logging; a runtime implementation will need to be provided.
Most messages are being logged to trace or debug, except for in the case where exceptions have been thrown.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>HttpCookieEnabled</artifactId>
        <version>1.0</version>
    </dependency>
```
