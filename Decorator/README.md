# Decorator

## Concepts
Mapping instance fields to properties that are in properties files can be cumbersome and introduce a lot of duplicated code.
This module seeks to alleviate that by providing one with the ability to decorate particular class fields directly using standard reflection. 

## Usage
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

## Supported Types
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

## Unsupported Scenarios
The following scenarios are not supported by this module:
*   Inner classes
*   Static classes or fields
*   Abstract classes
*   Fields that are final

## Logging
All logging is done via SLF4J. You will need to provide your own runtime implementations.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>Decorator</artifactId>
        <version>${decorator.version}</version>
    </dependency>
```
