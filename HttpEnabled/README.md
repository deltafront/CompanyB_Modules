# HttpEnabled

## Concept
This module is meant to facilitate easier web application design by encapsulating much of the boilerplate code for dealing with Cookies and Sessions into reusable methods.

### companyB.http.cookie

#### Concept
This package is designed to remove some of the boilerplate code around setting cookies that are required in order for a
certain web application to function. This package is designed so that all of the required cookies are defined in a configuration
file and loaded at runtime via the application.

#### Usage
1. First, you will need to make sure that there exists at least one text file that contains values for the cookie(s) that are required to be set. This text file has to have the following header (See section [Cookie Definition file] below for more information on each of the fields):

 ```
 #name,value,domain,maxAge,path,secure,version,comment,httpOnly
 ```
2. Get the list of Default Cookies from the reader:

 ```java
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

#### Cookie Definition file
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

#### Caching
For certain cookies it may be desirable to store the values in an external cache and write the key as the value into the cookie itself.
This holds true for cookie values that are of non-trivial length (anything over 2k) and  / or containing non-ASCII characters. To this end,
a CookieReader and CookieWriter class has been provided that can be injected with an implantation of [ExternalCache](https://github.com/deltafront/CompanyB_Modules/tree/master/HttpCacheEnabled).
*   **CookieReader**
    *   *Without Caching* - Values read from the cookies are to be treated as the actual values.
    *   *With Caching* - Values read from the cookies are keys, which are then in turn used for cache lookups.
*   **CookieWriter**
     *   *Without Caching* - The actual values are written to the cookies.
     *   *With Caching* - The actual value is written to the cache; what is written as the cookie's value is a generated key.

Implementations of ExternalCache are passed in via the single-arg constructor of these classes.
```java
    ExternalCache cache = new MemoryExternalCache("memory",Integer.MAX_VALUE);
    CookieWriter writer = new CookieWriter(cache);
    CookieReader reader = new CookieReader(cache);
```

### companyB.http.session

#### Concept
This  package allows you to define and access attributes that should be present in every session.

#### Usage
* Create a Session Definition file.The file must contain one line adhering to the following:
  * If maxInterval is to be defined:
  ```
    42=foo,bar,one,two
  ```
  * If maxInterval is not to be defined:
  ```
    foo,bar,one,two
   ```
* Read this file:
```java
    DefaultSessionAttributes defaultSessionAttributes= DefaultSessionAttributesReader.readDefaultSessionAttributes("foo.properties");
```
* Use the utils to set and get these attributes:
```java
    DefaultSessionUtils utils = new DefaultSessionUtils(defaultSessionAttributes);
    utils.setDefaultSessionAttribute(request, "foo",42,true,true);
    Object value = utils.getDefaultSessionAttribute(request,"foo",false);
```
# companyB.http.site

## Concept
This package is designed to remove some of the boilerplate code around getting and setting various attributes and capabilities
that are to be made available globally within the context of a web application. This is done via three discreet concepts:
  1. Site - Information about the site as a whole.
  2. Context - Information about a particular operation within the site, usually bound to a specific page.
  3. UserContext - Container for activities that a user has performed on the site.

## Usage
  * Create a new Site reference:
```java
    Site site = new Site("main","123",IsoLang.English,new IsoLang[]{IsoLang.Abkhazian,IsoLang.Afan_Oromo,IsoLang.Afrikaans},IsoLocale.UnitedStates);
```
  * For each operation on a page, create a new Context and associate it with the current session:
```java
    Context context = new Context("login.html", "register", site, "myLogin");
    ContextUtils.wrapContext(request.getSession(),context);
```
  * When the operation is ended, get the context from the session and end the operation:
```java
    context = ContextUtils.unwrapContext(request.getSession(), "myLogin");
    context.endOp();
```
  * To start or continue a record of User Activity, use the UserContext:
```java
    UserContext userContext = new UserContext("user:foo");
    //or if the userContext exists already
    //userContext = UserContextUtils.unwrapUserContext(request.getSession());
    userContext.addActivity(context);
    UserContextUtils.wrapUserContext(request.getSession());
```

## Logging
SLF4J is being used as a facade for logging; a runtime implementation will need to be provided.
Most messages are being logged to trace or debug, except for in the case where exceptions have been thrown.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>HttpEnabled</artifactId>
        <version>${http.enabled.version}</version>
    </dependency>
```
