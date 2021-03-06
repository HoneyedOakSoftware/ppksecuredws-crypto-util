package com.honeyedoak.cryptoutils.processor.asymmetric;

import com.google.auto.service.AutoService;
import com.honeyedoak.cryptoutils.annotation.AsymmetricCryptoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("com.honeyedoak.cryptoutils.annotation.AsymmetricCryptoService")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class CryptoutilsAsymmetricCryptoServiceProcessor extends AbstractProcessor {
	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

		// Iterate over all @Factory annotated elements
		for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AsymmetricCryptoService.class)) {
			// Check if a class has been annotated with @Factory
			if (annotatedElement.getKind() != ElementKind.PACKAGE) {
				error(annotatedElement, "Only packages can be annotated with @%s",
						AsymmetricCryptoService.class.getSimpleName());
				return true; // Exit processing
			}

			PackageElement packageElement = (PackageElement) annotatedElement;
			try {
				AsymmetricCryptoServiceAnnotatedPackage annotatedPackage = new AsymmetricCryptoServiceAnnotatedPackage(packageElement);

				if (!isValid(annotatedPackage)) {
					return true; // Exit processing
				}

				annotatedPackage.generateCode(filer);

			} catch (IllegalArgumentException e) {
				error(annotatedElement, e.getMessage());
				return true; // Exit processing
			} catch (IOException e) {
				error(annotatedElement, "Failed to write java class generated by annotation @%s",
						AsymmetricCryptoService.class.getSimpleName());
				return true; // Exit processing
			}
		}

		return true;
	}

	private boolean isValid(AsymmetricCryptoServiceAnnotatedPackage annotatedPackage) {
		PackageElement annotatedPackageElement = annotatedPackage.getAnnotatedPackageElement();
		for (Element element : annotatedPackageElement.getEnclosedElements()) {
			if (element.getKind().isClass() && element.getSimpleName().toString().equals(annotatedPackage.getServiceName())) {
				error(annotatedPackageElement,
						"The package %s annotated with @%s already contains a class or interface with name %s",
						annotatedPackageElement.getSimpleName().toString(),
						AsymmetricCryptoService.class.getSimpleName(),
						annotatedPackage.getServiceName());
				return false;
			}
		}

		return true;
	}

	private void error(Element e, String msg, Object... args) {
		messager.printMessage(
				Diagnostic.Kind.ERROR,
				String.format(msg, args),
				e);
	}
}
