## Cache

### Concept
Sometimes, values may be too large to handle via normal means, such as HTTP requests. Caching these values allows you
to provide shorter keys as values and perform a lookup when these parameters are passed to the service.

### Usage
*   Declare an implementation of ExternalCache in your class:
    ```java
        ExternalCache cache = new DefaultExternalCache("in_memory",100);
    ```
*   Use this to perform the following operations:
    *   Insert values to be cached
    ```java
        cache.insert("key","value");
    ```
    *   Retrieve cached values
    ```java
        String retrievedFromCache = cache.get("key");
    ```
    *   Remove cached values
    ```java
        String removedFromCache = cache.remove("key");
    ```

### Implementations
This module comes with three implementations:
*   Memory `companyB.http.cache.impl.memory.MemoryExternalCache`
    *   This cache is backed by an internally synchronized hash map.
    *   Usage of this cache is *not* recommended in Production; this should be used for development and testing *only*.
    *   To construct a new instance, two parameters are passed in via the constructor:
        *   **name** - Name of the cache. This is required.
        *   **initialLoad** - Initial load of the of the cache.
            *   This is required when using the two-args constructor.
            *   If the single-arg constructor is used, this defaults to Integer.MAX_VALUE.
*   Redis `companyB.http.cache.impl.redis.RedisExternalCache`
    *   This cache is backed by a Redis instance.
    *   This is the preferred cache to use if you require a solution that is accessible from multiple applications.
    *   To construct a new instance, four parameters are passed in via the constructor:
        *   **host** - Machine that the Redis instance is hosted on.
        *   **port** - Port that the Redis instance is listening on.
        *   **db**  - Redis database to use.
        *   **cacheName** - Name of the cache.
*   Hibernate `companyB.http.cache.impl.db.HibernateExternalCache`
    *   This cache uses a database.
    *   To construct a new instance, one required and one optional parameter is passed in via the constructor:
        *   **name**(required) - Name of the cache.
        *   **configuration**(optional) - Configurations object that has all of the required DB connection information needed for Hibernate to connect to the database.
    If a configuration is not passed in via the constructor,  then a properly configured `hibernate.cfg.xml` file needs to be present on the classpath.
*   Guava `companyB.http.cache.impl.guava.GuavaExternalCache`
    *   This cache is backed by Google Guava Cache.
    *   This is the preferred solution for memory-based caching.
    *   Required attributes for all constructors:
        *   **name** Name of the cache. Required.
    *   Constructor parameters for size based eviction:
        *   **maxSize** Maximum size of cache. Required. Must be an integer greater that '0'.
    *   Constructor parameters for read/ access based eviction:
        *   **duration** Duration of 'timeUnit', after which entries will be evicted. Required. Must be greater than '0'.
        *   **timeUnit** java.util.concurrent.TimeUnit Required.
        *   **expireAfterWrite** If this is set to true, then entries will be expired after their creation or replacement. If this is set to false, then entries will  be expired after their creation or access.

## Common

### Purpose
This package contains reusable components for consumption by other modules and applications.

### Top Level Packages

#### AtomicOptional

#### Conversion
`companyB.conversion.Converter` converts Strings representations into various supported datatypes.

#### GUID
This Serializable class facilitates the creation of simple Globally Unique Identifiers.

### Utilities
The following utility classes are available via this module. For each of the following modules, consult the JavaDocs
for further information.

#### Collections Splitter
Splits Collection into a List of Lists, either into `n` number of equally sized lists, or `x` number of lists each of which
has at a minimum of `n` items.

#### Factory Utils
Instantiates classes; optionally provides singleton instances.

#### FieldUtils

#### Properties Utils
Provides a simple wrapper for `java.util.Properties`. It allows a property to be retrieved using a single-line of code.

#### Query Mapper
This class takes a Http Query String and returns a mapping of all of its elements.

#### RunTimeUtils
Provides a one-step utility for executing simple commands.

#### ServletRequestUtils
This class contains convenience methods for dealing with Servlet Requests. Presently, the only method in this class is `getRequestBody(request)`.

#### ServletResponseUtils
This class contains convenience methods for dealing with Servlet Responses. Presently, the only method in this class is `writeResponse(response, message, flush)`.

## EventLogger

### Concept
This package provides a single point of contact for logging specific events or even just plain messages in a unified
format. This makes it possible to develop logging messages that can be written company-wide and easily indexed and
searched by applications such as Splunk. This module consists of four distinct parts:

1. **EventLogger**: This class actually performs the logging.
2. **EventCode**: This class represents an Event Code as specified by the user.
3. **LogMessageFormatter**: Implementations of this class specify how to format the logging messages themselves.
4. **EventLoggerFactory**: Provides singleton instances of EventLogger.

### Usage
1. Define an EventCode for use:
    ```java
        EventCode defaultEvent = new EventCode("foo",42);
    ```
2. Get an EventLogger from the EventLoggerFactory:
    i. Binding to a class:
        ```java
            EventLoggerFactory factory = new EventLoggerFactory();
            EventLogger logger = factory.getEventLogger(Foo.class);
        ```
    ii. Binding to a name:
    ```java
        EventLogger logger = factory.getEventLogger("Foo");
    ```
    iii. Binding to a specific instance:
    ```java
        EventLogger logger = factory.getEventLogger(this);
    ```
3. (Optional) Add an implementation of LogMessageFormatter:
    ```java
        EventLogger logger = factory.getEventLogger(this).withFormatter(new CustomMessageFormatter());
    ```
If this step is skipped, an instance of DefaultLogMessageFormatter will be used.
4.  Use this logger to log messages:
    ```java
        logger.info(defaultEvent,"this is a message",null,null);
    ```
    You can optionally supply properties and a throwable to the message to be logged.

#### Decoration
Optionally, you can use the EventLoggerFactory.decorate() method in conjunction with the `@EventLog` annotation to decorate
fields within your classes / instances with instances of EventLogger:
1.  Decorate your field:
    ```java
        @EventLog
        private EventLogger eventLogger;
    ```
2.  Use the factory method to decorate the field:
    ```java
        Foo foo = EventLoggerFactory.decorate(Foo.class);
        // -or-
        Foo foo = EventLoggerFactory.decorate(new Foo());
     ```
The `@EventLog` supports two optional parameters:
*   **name**(String)  - The name of the EventLogger. If this is not supplied, then the canonical class name is used.
*   **logMessageFormatter**(Class) - Class that implements LogMessageFormatter.
    If this is not supplied then an instance of DefaultLogMessageFormatter is constructed and attached to the EventLogger.
    If this **is** supplied, then the implementing class is required to have a single no-args publicly accessible constructor.

## JDBC


## Logging
All logging is done via SLF4J. You will need to provide your own runtime implementations.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>Common</artifactId>
        <version>${common.version}</version>
    </dependency>
```