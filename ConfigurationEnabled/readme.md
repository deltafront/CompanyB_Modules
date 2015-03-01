# ConfigurationEnabled

## Concepts
ConfigurationEnabled allows several identically named properties to exist in the same property file delineated only by
 a contextual family. This allows for many different classes to make use of the same properties within the same property
 file, the only difference being the individual property's context.

## Examples
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

## Usage

There are two ways that a ConfigEnabler can be utilized:

1.  **Ad-Hoc**
    1.  Declare a new instance of ConfigEnabler, feeding it the name of the property file and the contextual family needed:
        ```
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

|Data Type  |Default Value  |
 ----------------------------
|short	    |0              |
|int	    |0              |
|long	    |0L             |
|float	    |0.0F           |
|double	    |0.0D           |
|String     |null           |
|boolean	|false          |

*   Each instance of ConfigEnabler will return values based upon a single properties filename / contextual family paring. 
**  If a different contextual family from the same properties file is needed, a separate instance of ConfigEnabler will need to be instantiated.
For this case, however, the performance impact should be minimal, since all contextual families within a single properties file are loaded and 
retained in memory for the duration of the application session.
**  If a contextual family from a different properties file is needed, a separate instance of ConfigEnabler. This will cause all of the contextual
families from that properties file to be loaded and added to any previously existing properties file contextual family mappings.
Due to the key behavior in each of the cases above, it is recommended that only **absolute paths** are specified, and **not** classpath
resources!

## Logging
*   This module makes use of the slf4j logging facade. A logging implementation should be supplied at runtime. 
*   All returns from methods are logged at *debug*. All other statements are logged at *trace*, unless they are in a catch block,
    which case they are logged to *error* and include both the message and the stack trace.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>ConfigurationEnabled</groupId>
        <artifactId>Decorator</artifactId>
        <version>1.0</version>
    </dependency>
```
