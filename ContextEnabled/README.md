# ContextEnabled

## Purpose
This module is meant to provide the interface for (and default implantation of) a thread-safe application context that can 
be shared between classes running within a single application.

## Usage
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

## Spring configuration
See [sample-context-config.xml] for an example as to how this could be wired to work with Spring. It should be noted though 
that if you are going to go through the trouble of attempting to wire this module to work with Spring, it might be best to 
just use Springs native function instead of this ;)

## Notes
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
  
## Logging
This module makes use of the SLF4J logging facade. All messages are logged to either trace or, if exceptions have been thrown, 
error. Facade implementations will need to be provided at runtime.

## Maven Dependency Declaration
```xml
    <dependency>
        <groupId>companyB</groupId>
        <artifactId>ContextEnabled</artifactId>
        <version>${context.enabled.version}</version>
    </dependency>
```