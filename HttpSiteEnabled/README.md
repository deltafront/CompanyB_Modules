# HttpSiteEnabled

## Concept
This module is designed to remove some of the boilerplate code around getting and setting various attributes and capabilities
that are to be made available globally within the context of a web application. This is done via three discreet concepts:
  1. Site - Information about the site as a whole.
  2. Context - Information about a particular operation within the site, usually bound to a specific page.
  3. UserContext - Container for activities that a user has performed on the site.

## Usage
  * Create a new Site reference:
```java
    Site site = new Site("main","123",IsoLanguage.English,new IsoLanguage[]{IsoLanguage.Abkhazian,IsoLanguage.Afan_Oromo,IsoLanguage.Afrikaans},IsoLocale.UnitedStates);
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

## SiteResolver
Site resolvers are a way to get the data related to a Site from an external source.
This capability is specified via implementations of `SiteResolver`:
```java
public interface SiteResolver
    {
        public Site resolveSite(String host,Integer port);
    }
```
The primary method, `resolveSite`, takes two parameters:
*   **host** - Host / cluster on which the site is being run.
*   **port** - Port that the WebApp container for this. This can be a null value.

The default implementation of `SiteResolver`, `DefaultSiteResolver`, returns a Site with the following characteristics:
*   **siteName** - Concatenation of the machine's host name and specified port.
*   **siteId**  - '0'.
*   **primaryLanguage** - 'EN'(English).
*   **supportedLanguages** - 'EN'(English).
*   **locale** - 'US' (United States).

## Logging
SLF4J is being used as a facade for logging; a runtime implementation will need to be provided.
Most messages are being logged to trace or debug, except for in the case where exceptions have been thrown.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>HttpSiteEnabled</artifactId>
        <version>${http.site.enabled.version}</version>
    </dependency>
```
