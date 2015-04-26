# EncryptedEnabled

## Purpose
This module provides an easy way to mark String fields as being Encrypted and encrypt them using the decorator pattern.

## Usage
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

## Caveats
*   Only Strings are supported at this time.
*   Only non-static, non-final fields are supported.

## Logging
SLF4J is used to provide the logging facade for this module. A specific implementation must be provided at runtime.
