package com.honeyedoak.cryptoutils.processor.asymmetric;

import com.honeyedoak.cryptoutils.annotation.AsymmetricCryptoService;
import com.honeyedoak.cryptoutils.AsymmetricCryptoUtils;
import com.honeyedoak.cryptoutils.AsymmetricCryptoUtilsImpl;
import com.honeyedoak.cryptoutils.exception.CryptoException;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricCryptoServiceAnnotatedPackage {

	private PackageElement annotatedPackageElement;
	private String serviceName;
	private String algorithm;
	private int keySize;

	public AsymmetricCryptoServiceAnnotatedPackage(PackageElement packageElement) throws IllegalArgumentException {
		this.annotatedPackageElement = packageElement;
		AsymmetricCryptoService annotation = packageElement.getAnnotation(AsymmetricCryptoService.class);
		serviceName = annotation.serviceName();
		algorithm = annotation.algorithm();
		keySize = annotation.keySize();

		if (StringUtils.isEmpty(serviceName)) {
			throw new IllegalArgumentException(
					String.format("serviceName() in @%s for class %s is null or empty! that's not allowed",
							AsymmetricCryptoService.class.getSimpleName(), packageElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(algorithm)) {
			throw new IllegalArgumentException(
					String.format("algorithm() in @%s for class %s is null or empty! that's not allowed",
							AsymmetricCryptoService.class.getSimpleName(), packageElement.getQualifiedName().toString()));
		}

		if (keySize < 1) {
			throw new IllegalArgumentException(
					String.format("keySize() in @%s for class %s is zero or negative! that's not allowed",
							AsymmetricCryptoService.class.getSimpleName(), packageElement.getQualifiedName().toString()));
		}
	}

	public PackageElement getAnnotatedPackageElement() {
		return annotatedPackageElement;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public int getKeySize() {
		return keySize;
	}

	public void generateCode(Filer filer) throws IOException {
		FieldSpec asymmetricCryptoUtils = FieldSpec.builder(AsymmetricCryptoUtils.class, "asymmetricCryptoUtils", Modifier.PRIVATE, Modifier.FINAL)
				.build();

		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addCode("this.asymmetricCryptoUtils = new $T($S, $L);", AsymmetricCryptoUtilsImpl.class, algorithm, keySize)
				.build();

		MethodSpec generateKeyPair = MethodSpec.methodBuilder("generateKeyPair")
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addException(CryptoException.class)
				.returns(KeyPair.class)
				.addCode("return this.asymmetricCryptoUtils.generateKeyPair();")
				.build();

		MethodSpec decodePrivateKey = MethodSpec.methodBuilder("decodePrivateKey")
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addException(CryptoException.class)
				.returns(PrivateKey.class)
				.addParameter(byte[].class, "encodedKey")
				.addCode("return asymmetricCryptoUtils.decodePrivateKey(encodedKey);")
				.build();

		MethodSpec decodePublicKey = MethodSpec.methodBuilder("decodePublicKey")
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addException(CryptoException.class)
				.returns(PublicKey.class)
				.addParameter(byte[].class, "encodedKey")
				.addCode("return asymmetricCryptoUtils.decodePublicKey(encodedKey);")
				.build();

		MethodSpec decrypt = MethodSpec.methodBuilder("decrypt")
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addException(CryptoException.class)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(Key.class, "key")
				.addCode("return asymmetricCryptoUtils.decrypt(payload, key);")
				.build();

		MethodSpec encrypt = MethodSpec.methodBuilder("encrypt")
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addException(CryptoException.class)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(Key.class, "key")
				.addCode("return asymmetricCryptoUtils.encrypt(payload, key);")
				.build();

		TypeSpec asymmetricCryptoService = TypeSpec.classBuilder(serviceName)
				.addAnnotation(Generated.class)
				.addModifiers(Modifier.PUBLIC)
				.addSuperinterface(AsymmetricCryptoService.class)
				.addField(asymmetricCryptoUtils)
				.addAnnotation(Service.class)
				.addMethod(constructor)
				.addMethod(generateKeyPair)
				.addMethod(decodePrivateKey)
				.addMethod(decodePublicKey)
				.addMethod(decrypt)
				.addMethod(encrypt)
				.build();

		JavaFile.builder(annotatedPackageElement.getQualifiedName().toString(), asymmetricCryptoService)
				.build()
				.writeTo(filer);
	}
}
