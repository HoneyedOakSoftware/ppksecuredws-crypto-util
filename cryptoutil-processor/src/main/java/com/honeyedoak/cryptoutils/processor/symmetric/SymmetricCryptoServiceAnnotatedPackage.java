package com.honeyedoak.cryptoutils.processor.symmetric;

import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import java.io.IOException;

public class SymmetricCryptoServiceAnnotatedPackage {

	private PackageElement annotatedPackageElement;
	private String serviceName;
	private String algorithm;

	public SymmetricCryptoServiceAnnotatedPackage(PackageElement packageElement) throws IllegalArgumentException {
		this.annotatedPackageElement = packageElement;
		com.honeyedoak.cryptoutils.annotation.SymmetricCryptoService annotation = packageElement.getAnnotation(com.honeyedoak.cryptoutils.annotation.SymmetricCryptoService.class);
		serviceName = annotation.serviceName();
		algorithm = annotation.algorithm();

		if (StringUtils.isEmpty(serviceName)) {
			throw new IllegalArgumentException(
					String.format("serviceName() in @%s for class %s is null or empty! that's not allowed",
							com.honeyedoak.cryptoutils.annotation.SymmetricCryptoService.class.getSimpleName(), packageElement.getQualifiedName().toString()));
		}

		if (StringUtils.isEmpty(algorithm)) {
			throw new IllegalArgumentException(
					String.format("algorithm() in @%s for class %s is null or empty! that's not allowed",
							com.honeyedoak.cryptoutils.annotation.SymmetricCryptoService.class.getSimpleName(), packageElement.getQualifiedName().toString()));
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

	public void generateCode(Filer filer) throws IOException {
		FieldSpec symmetricCryptoUtils = FieldSpec.builder(com.honeyedoak.cryptoutils.SymmetricCryptoUtils.class, "symmetricCryptoUtils", Modifier.PRIVATE, Modifier.FINAL)
				.build();

		MethodSpec constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addCode("this.symmetricCryptoUtils = new $T($S);", com.honeyedoak.cryptoutils.SymmetricCryptoUtilsImpl.class, algorithm)
				.build();

		MethodSpec decrypt = MethodSpec.methodBuilder("decrypt")
				.addAnnotation(java.lang.Override.class)
				.addModifiers(Modifier.PUBLIC)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(String.class, "password")
				.addCode("return symmetricCryptoUtils.decrypt(payload, password);")
				.build();

		MethodSpec decryptChar = MethodSpec.methodBuilder("decrypt")
				.addAnnotation(java.lang.Override.class)
				.addModifiers(Modifier.PUBLIC)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(char[].class, "password")
				.addCode("return symmetricCryptoUtils.decrypt(payload, password);")
				.build();

		MethodSpec encrypt = MethodSpec.methodBuilder("encrypt")
				.addAnnotation(java.lang.Override.class)
				.addModifiers(Modifier.PUBLIC)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(String.class, "password")
				.addCode("return symmetricCryptoUtils.encrypt(payload, password);")
				.build();

		MethodSpec encryptChar = MethodSpec.methodBuilder("encrypt")
				.addAnnotation(java.lang.Override.class)
				.addModifiers(Modifier.PUBLIC)
				.returns(byte[].class)
				.addParameter(byte[].class, "payload")
				.addParameter(char[].class, "password")
				.addCode("return symmetricCryptoUtils.encrypt(payload, password);")
				.build();

		AnnotationSpec generated = AnnotationSpec.builder(javax.annotation.Generated.class)
				.addMember("value", "$S", com.honeyedoak.cryptoutils.processor.asymmetric.CryptoutilsAsymmetricCryptoServiceProcessor.class.getName())
				.build();

		TypeSpec asymmetricCryptoService = TypeSpec.classBuilder(serviceName)
				.addAnnotation(generated)
				.addModifiers(Modifier.PUBLIC)
				.addSuperinterface(com.honeyedoak.cryptoutils.annotation.SymmetricCryptoService.class)
				.addAnnotation(org.springframework.stereotype.Service.class)
				.addField(symmetricCryptoUtils)
				.addMethod(constructor)
				.addMethod(decrypt)
				.addMethod(decryptChar)
				.addMethod(encrypt)
				.addMethod(encryptChar)
				.build();

		JavaFile.builder(annotatedPackageElement.getQualifiedName().toString(), asymmetricCryptoService)
				.build()
				.writeTo(filer);
	}
}
