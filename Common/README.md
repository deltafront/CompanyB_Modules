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

### Utilities
The following utility classes are available via this module. For each of the following modules, consult the JavaDocs
for further information.

#### Collections Splitter
Splits Collection into a List of Lists, either into `n` number of equally sized lists, or `x` number of lists each of which
has at a minimum of `n` items.

#### Factory Utils
Instantiates classes; optionally provides singleton instances.

#### Properties Utils
Provides a simple wrapper for `java.util.Properties`. It allows a property to be retrieved using a single-line of code.

#### Simple Regex Utils
Contains utilities for evaluation of string content.

#### ToStringUtils
Provides custom representations of Iterables and Maps.

#### RunTimeUtils
Provides a one-step utility for executing simple commands.

#### Converter
This class converts Strings representations into various supported datatypes.

#### NodeIterEnum
This class implements both `java.util.Iterator` and `java.util.Enumeration`. Internally, it is a primitive node-based singly linked list.

#### GUID
This Serializable class facilitates the creation of simple Globally Unique Identifiers.

#### ServletRequestUtils
This class contains convenience methods for dealing with Servlet Requests. Presently, the only method in this class is `getRequestBody(request)`.

#### ServletResponseUtils
This class contains convenience methods for dealing with Servlet Responses. Presently, the only method in this class is `writeResponse(response, message, flush)`.

#### Query Mapper
This class takes a Http Query String and returns a mapping of all of its elements.

## Configuration

### Concepts
ConfigurationEnabled allows several identically named properties to exist in the same property file delineated only by
 a contextual family. This allows for many different classes to make use of the same properties within the same property
 file, the only difference being the individual property's context.

### Examples
Following is an example of a typical configuration file:

```
family.1.foo=this
family.1.bar=true
family.1.baz=42
family.1.doubleBaz=42.00
family.2.foo=that
family.2.bar=false
family.2.baz=17
family.2.doubleBaz=17.00
```

In this instance, there are two contextual families:
*   "family.1"
*   "family.2"

Both families have exactly the same attributes, but this does not always have to be the case.

### Usage

There are two ways that a ConfigEnabler can be utilized:

1.  **Ad-Hoc**
    1.  Declare a new instance of ConfigEnabler, feeding it the name of the property file and the contextual family needed:

        ```java
            String fileName = "config.properties";
            String family = "foo";
            ConfigEnabler config = new ConfigEnabler(fileName,family);
        ```
    2.  Get the needed value from your now loaded configurations:
        ```
            Long sleepCycle = config.getLong("sleepCycle");
        ```

2. **Decorated**
    1.  In a class that has ConfigEnabler fields, decorate the fields using the 'ConfigEnabled' decoration:
        ```
        @ConfigEnabled(filename = "config.properties", family = "foo")
        private ConfigEnabler configEnabler;
        ```

    2.  Process an instance of that class through the ClassLevelConfigEnabler:
        ```
        AnnotatedClass toBeDecorated  = new AnnotatedClass();
        ClassLevelConfigEnabler.decorate(toBeDecorated);
        ```
    At this point, methods within the decorated class will have access to a fully instantiated instance of ConfigEnabler.


A few notes:
*   The currently supported data types are String, Integer, Long, Short, Double, Boolean, BigInteger, BigDecimal and Float.
*   Each of the get* methods are overloaded so that a default value can be specified to be returned if for some reason the desired key is not
 found.
*   If no default value is specified and the key is not found, the following values are returned:
  *   *short (Short)* - 0
  *   *int (Integer)* - 0
  *   *Big Integer* - 0
  *   *long (Long)*  - 0L
  *   *float (Float)* - 0F
  *   *double (Double)* - 0.0D
  *   *BigDecimal* - 0.0
  *   *String* - null
  *   *boolean (Boolean)* - false

*   Each instance of ConfigEnabler will return values based upon a single properties filename / contextual family paring.
  *  If a different contextual family from the same properties file is needed, a separate instance of ConfigEnabler will need to be instantiated.
For this case, however, the performance impact should be minimal, since all contextual families within a single properties file are loaded and
retained in memory for the duration of the application session.
  *  If a contextual family from a different properties file is needed, a separate instance of ConfigEnabler. This will cause all of the contextual
families from that properties file to be loaded and added to any previously existing properties file contextual family mappings.
Due to the key behavior in each of the cases above, it is recommended that only **absolute paths** are specified, and **not** classpath
resources!

## Context

### Purpose
This is meant to provide the interface for (and default implantation of) a thread-safe application context that can
be shared between classes running within a single application.

### Usage
1.  From within a class, instantiate an instance of I_ApplicationContext:
```java
    I_ApplicationContext context = new DefaultApplicationContext();
```
2.  Set a value that is be applied application-wide:
```java
    context.associate("foo","bar");
```
3.  Get a value that has previously been set:
```java
    String value = context.get("foo");
```
4.  Get an instance of an class from the context:
```java
    Object[]args = new Object{true,1,"this"}
    Foo foo = context.getInstance(Foo.class,args,"foo.1");
```
Note that this instance object can be retrieved from another class just by referring to it by its id:
 ```java
    Foo foo = context.get("foo.1");
 ```

### Spring configuration
See [sample-context-config.xml] for an example as to how this could be wired to work with Spring. It should be noted though
that if you are going to go through the trouble of attempting to wire this module to work with Spring, it might be best to
just use Springs native function instead of this ;)

### Notes
1.  All implementations of I_ApplicationContext should be immutable, meaning that one class should not be able to override the
values set from within another.
2.  Make sure that the constructor args used to initialize instances of classes are not ambiguous. Avoid classes that require concrete
implementations of Serializable, Cloneable, Comparable, and so on. The method that accomplishes the instantiation does so on a greedy
basis, meaning that it tries to instantiate the first best possible match. Avoid objects that have constructors such as:
```java
    public Foo(String one, Long two, Timestamp three)
    public Foo(Serializable one, Comparable two, BasicAttributes three)
```
3.  Object instantiated using `I_ApplicationContext.getInstance(Class, args, id)` are automatically associated with the provided key
  internally so that they can be made available Application-wide

## Decorator

### Concepts
Mapping instance fields to properties that are in properties files can be cumbersome and introduce a lot of duplicated code.
This module seeks to alleviate that by providing one with the ability to decorate particular class fields directly using standard reflection.

### Usage
1.  Annotate the fields that need to be decorated from a properties file
```java
#Use this format if the name of the field matches the name of the property in the properties file
@Decorated
private String foo;
#Use the 'alternateName' field if the name of the field does not match the property in the properties file
@Decorated(alternateName = "altFoo")
private int integerValue;
#Use the 'defaultValue' field if there is a default value that can be used in the case of when no value can be found.
@Decorated(defaultValue = "42")
private BigInteger bigIntValue;
```
2.  After you have loaded the properties from a properties file, pass the properties reference and the class definition to the BeanDecorator:
``` java
MyClass myClass = BeanDecorator.decorate(MyClass.class, properties);
```
Alternatively, if your Class requires special conditions to instantiate, you can instantiate an instance of it youself and then pass that instance in:
```java
MyClass myClass = new MyClass(args);
myClass = BeanDecorator.decorate(MyClass.class, properties);
```
Please note that XML properties files are supported.

### Supported Types
The following field types are supported:
*   int / Integer
*   long / Long
*   short / Short
*   char / Character
*   byte / Byte
*   double / Double
*   boolean / Boolean
*   BigInteger
*   BigDecimal
*   String

All other types will cause an UnsupportedTypeException during the course of execution.

### Unsupported Scenarios
The following scenarios are not supported by this module:
*   Inner classes
*   Static classes or fields
*   Abstract classes
*   Fields that are final

## Encrypted

### Purpose
This package provides an easy way to mark String fields as being Encrypted and encrypt them using the decorator pattern.

### Usage
*   Decorate the string fields in your class that you want to  have encrypted:
    ```java
        @Encrypted(algorithm = Encrypted.algorithms.MD2) //if algorithm is not supplied, default is 'md5'.
        private String foo;
     ```
     At some point, whether via the constructor or a setter method, the value for this field must be supplied.
*   Run the enclosing object instance through the decorator:
    ```java
        Foo foo = EncryptedDecorator.decorate(new Foo("this");
    ```
*   You can also decorate your fields with an instance of EncryptedWrapper, and then pass them through the decorator. Doing this will allow you to encrypt the String value of any object.
### Caveats
*   Only Strings are supported at this time.
*   Only non-static, non-final fields are supported.

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