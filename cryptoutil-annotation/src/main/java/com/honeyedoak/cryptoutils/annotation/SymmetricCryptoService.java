package com.honeyedoak.cryptoutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface SymmetricCryptoService {

	String serviceName() default "SymmetricCryptoService";

	String algorithm() default "PBEWITHSHA256AND128BITAES-CBC-BC";
}
