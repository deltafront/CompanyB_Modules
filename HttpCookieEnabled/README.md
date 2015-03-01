# HttpCookieEnabled

## Concept
This module is designed to remove some of the boilerplate code around setting cookies that are required in order for a 
certain web application to function. This module is designed so that all of the required cookies are defined in a configuration
file and loaded at runtime via the application. 

## Usage
1. First, you will need to make sure that there exists at least one text file that contains values for the cookie(s) that are required to be set.
This text file has to have the following header:
```
#name,value,domain,maxAge,path,secure,version,comment,httpOnly
```
See section [Cookie Definition file] below for more information

2. Get the list of Default Cookies from the reader:
``` java
List<DefaultCookie> defaultCookies = CookieReader.readCookiesFromFile(filename);
```

3. Use this list to either populate an instance of HttpServletResponse with the default cookies or get all of the existing
Default Cookie values from an instance of HttpServletRequest:
    I. Populate response:
    ```java
    DefaultCookieUtils utils = new DefaultCookieUtils(defaultCookies);
    utils.setDefaultCookies(response);
    ```
    II. Get Default Cookies
    ```java
    DefaultCookieUtils utils = new DefaultCookieUtils(defaultCookies);
    List<DefaultCookies> cookiesFromRequest = utils.getDefaultCookies(request);
    ```
    III. Get a Default Cookie from the request
    ```java
    DefaultCookieUtils utils = new DefaultCookieUtils(defaultCookies);
    Cookie cookie = utils.getDefaultCookie("Foo",request);
    ```
    IV. Set a new value for a Default Cookie
    ```java
    DefaultCookieUtils utils = new DefaultCookieUtils(defaultCookies);
    boolean cookieHasBeenSet = utils.setDefaultCookieValue("Foo","Bar",response);
    ```
Both of these operations could very easily be accomplished from within a Filter or a Servlet.
Once these values are loaded into Cookies, they can be updated at anytime before they are inserted into the response.
    
## Cookie Definition file
The Cookie Definition file must have at least two lines:
1. Header line
```
#name,value,domain,maxAge,path,secure,version,comment,httpOnly
```
2. At least one line that has the following information in this exact order:
    I. name 
    II. value
    III. domain
    IV. maxAge - This value must be a valid integer
    V. path
    VI. secure - This value must be either 'true' or 'false' (case insensitive).
    VII. version - This value must be  either '0' or '1'
    VIII. comment
    IX. httpOnly - This value must be either 'true' or 'false' (case insensitive).
    
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