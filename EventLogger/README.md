# EventLogger

## Concept
This module provides a single point of contact for logging specific events or even just plain messages in a unified
format. This makes it possible to develop logging messages that can be written company-wide and easily indexed and 
searched by applications such as Splunk. This module consists of four distinct parts:

1. **EventLogger**: This class actually performs the logging.
2. **EventCode**: This class represents an Event Code as specified by the user.
3. **LogMessageFormatter**: Implementations of this class specify how to format the logging messages themselves.
4. **EventLoggerFactory**: Provides singleton instances of EventLogger.

## Usage
1. Define an EventCode for use:
    ```java
        EventCode defaultEvent = new EventCode("foo",42);
    ```
2. Get an EventLogger from the EventLoggerFactory:
    i. Binding to a class:
        ```java
            EventLogger logger = EventLoggerFactory.getEventLogger(Foo.class);
        ```
    ii. Binding to a name:
    ```java
        EventLogger logger = EventLoggerFactory.getEventLogger("Foo");
    ```
    iii. Binding to a specific instance:
    ```java
        EventLogger logger = EventLoggerFactory.getEventLogger(this);
    ```
3. (Optional) Add an implementation of LogMessageFormatter:
    ```java
        EventLogger logger = EventLoggerFactory.getEventLogger(this).withFormatter(new CustomMessageFormatter());
    ```
If this step is skipped, an instance of DefaultLogMessageFormatter will be used.
4.  Use this logger to log messages:
    ```java
        logger.info(defaultEvent,"this is a message",null,null);
    ```
    You can optionally supply properties and a throwable to the message to be logged. 

### Decoration
Optionally, you can use the EventLoggerFactory.decorate() method in conjunction with the @EventLog annotation to decorate
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
        Foo foo = EvengLoggerFactory.decorate(new Foo());
     ```
## Logging
All logging is done via SLF4J. You will need to provide your own runtime implementations.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>FlywayEnabled</artifactId>
        <version>${event.logger.version}</version>
    </dependency>
```
