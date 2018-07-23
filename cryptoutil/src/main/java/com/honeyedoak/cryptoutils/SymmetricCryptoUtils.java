package com.honeyedoak.cryptoutils;

public interface SymmetricCryptoUtils {
	byte[] encrypt(byte[] payload, String password);

	byte[] encrypt(byte[] payload, char[] password);

	byte[] decrypt(byte[] payload, String password);

	byte[] decrypt(byte[] payload, char[] password);
}
