# Common

## Purpose
This module contains reusable components for consumption by other modules and applications.

## Utilities
The following utility classes are available via this module. For each of the following modules, consult the JavaDocs
for further information.

### Collections Splitter
Splits Collection into a List of Lists, either into `n` number of equally sized lists, or `x` number of lists each of which
has at a minimum of `n` items.

### Factory Utils
Instantiates classes; optionally provides singleton instances.

### Properties Utils
Provides a simple wrapper for `java.util.Properties`. It allows a property to be retrieved using a single-line of code.

### Simple Regex Utils
Contains utilities for evaluation of string content.

### ToStringUtils
Provides custom representations of Iterables and Maps.

### RunTimeUtils
Provides a one-step utility for executing simple commands.

### Converter
This class converts Strings representations into various supported datatypes.

### NodeIterEnum
This class implements both `java.util.Iterator` and `java.util.Enumeration`. Internally, it is a primitive node-based singly linked list.

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