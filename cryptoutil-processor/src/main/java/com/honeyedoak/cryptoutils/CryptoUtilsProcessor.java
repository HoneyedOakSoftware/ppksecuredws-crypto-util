package com.honeyedoak.cryptoutils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.honeyedoak.cryptoutils")
public class CryptoUtilsProcessor {
	public static void main(String[] args) {
		SpringApplication.run(CryptoUtilsProcessor.class, args);
	}
}