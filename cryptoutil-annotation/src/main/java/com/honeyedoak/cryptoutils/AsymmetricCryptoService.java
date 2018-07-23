package com.honeyedoak.cryptoutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface AsymmetricCryptoService {

	String serviceName() default "AsymmetricCryptoService";

	String algorithm() default "RSA";

	int keySize() default 4096;
}
