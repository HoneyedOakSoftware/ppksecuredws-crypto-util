# ppksecuredws-crypto-util

automatic generation of CryptotulsService classes to reduce boilerplate code

## provided annotations
```java
@AsymmetricCryptoService(serviceName = "AsymmetricCryptoService", algorithm = "rsa", keysize = 4096) //all params are optional, shown values are provided defaults
@SymmetricCryptoService(serviceName = "SymmetricCryptoService", algorithm = "PBEWITHSHA256AND128BITAES-CBC-BC") //all params are optional, shown values are provided defaults
```

Both are meant to set on package level where they will generate their respective @Serives, each implementing com.honeyedoak.cryptoutils.AsymetricCryptoUtils or com.honeyedoak.cryptoutils.SymetricCryptoUtils interfaces.

### AsymmetricCryptoService
example of generated class:
```java
//TODO
```

### SymmetricCryptoService
example of generated class:
```java
//TODO
```
