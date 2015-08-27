# CacheEnabled

## Concept
Sometimes, values may be too large to handle via normal means, such as HTTP requests. Caching these values allows you
to provide shorter keys as values and perform a lookup when these parameters are passed to the service.

## Usage
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
    
## Implementations
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

## Logging
This module uses the SLF4J logging facade. A runtime implementation will need to be provided. All messages are logged to TRACE.

## Maven dependency
This module can be imported using the following dependency declaration in the POM:
```
<dependency>
    <groupId>companyB</groupId>
    <artifactId>CacheEnabled</artifactId>
    <version>${cache.enabled.version}</version>
</dependency>
```