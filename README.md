# ppksecuredws-crypto-util

automatic generation of CryptoService classes to reduce boilerplate.

## provided annotations
```java
@AsymmetricCryptoService(serviceName = "AsymmetricCryptoService", algorithm = "rsa", keysize = 4096) //all params are optional, shown values are provided defaults
@SymmetricCryptoService(serviceName = "SymmetricCryptoService", algorithm = "PBEWITHSHA256AND128BITAES-CBC-BC") //all params are optional, shown values are provided defaults
```

Both are meant to set on package level where they will generate their respective @Services, each implementing com.honeyedoak.cryptoutils.AsymetricCryptoUtils or com.honeyedoak.cryptoutils.SymetricCryptoUtils interfaces.

### AsymmetricCryptoService
example of generated class:
```java
package com.honeyedoak.cryptoutils.test.service;

import com.honeyedoak.cryptoutils.AsymmetricCryptoUtils;
import com.honeyedoak.cryptoutils.AsymmetricCryptoUtilsImpl;
import com.honeyedoak.cryptoutils.exception.CryptoException;
import java.lang.Override;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.springframework.stereotype.Service;

@Service
public class AsymmetricCryptoService implements com.honeyedoak.cryptoutils.AsymmetricCryptoService {
  private final AsymmetricCryptoUtils asymmetricCryptoUtils;

  AsymmetricCryptoService() {
    this.asymmetricCryptoUtils = new AsymmetricCryptoUtilsImpl("RSA", 4096);}

  @Override
  public KeyPair generateKeyPair() throws CryptoException {
    return this.asymmetricCryptoUtils.generateKeyPair();}

  @Override
  public PrivateKey decodePrivateKey(byte[] encodedKey) throws CryptoException {
    return asymmetricCryptoUtils.decodePrivateKey(encodedKey);}

  @Override
  public PublicKey decodePublicKey(byte[] encodedKey) throws CryptoException {
    return asymmetricCryptoUtils.decodePublicKey(encodedKey);}

  @Override
  public byte[] decrypt(byte[] payload, Key key) throws CryptoException {
    return asymmetricCryptoUtils.decrypt(payload, key);}

  @Override
  public byte[] encrypt(byte[] payload, Key key) throws CryptoException {
    return asymmetricCryptoUtils.encrypt(payload, key);}
}
```

### SymmetricCryptoService
example of generated class:
```java
package com.honeyedoak.cryptoutils.test.service;

import com.honeyedoak.cryptoutils.SymmetricCryptoUtils;
import com.honeyedoak.cryptoutils.SymmetricCryptoUtilsImpl;
import java.lang.Override;
import java.lang.String;
import org.springframework.stereotype.Service;

@Service
public class SymmetricCryptoService implements com.honeyedoak.cryptoutils.SymmetricCryptoService {
  private final SymmetricCryptoUtils symmetricCryptoUtils;

  SymmetricCryptoService() {
    this.symmetricCryptoUtils = new SymmetricCryptoUtilsImpl("PBEWITHSHA256AND128BITAES-CBC-BC");}

  @Override
  public byte[] decrypt(byte[] payload, String password) {
    return symmetricCryptoUtils.decrypt(payload, password);}

  @Override
  public byte[] decrypt(byte[] payload, char[] password) {
    return symmetricCryptoUtils.decrypt(payload, password);}

  @Override
  public byte[] encrypt(byte[] payload, String password) {
    return symmetricCryptoUtils.encrypt(payload, password);}

  @Override
  public byte[] encrypt(byte[] payload, char[] password) {
    return symmetricCryptoUtils.encrypt(payload, password);}
}
```
