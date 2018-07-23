package com.honeyedoak.cryptoutils;

import com.honeyedoak.cryptoutils.exception.CryptoException;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface AsymmetricCryptoUtils {
	KeyPair generateKeyPair() throws CryptoException;

	PrivateKey decodePrivateKey(byte[] encodedKey) throws CryptoException;

	PublicKey decodePublicKey(byte[] encodedKey) throws CryptoException;

	byte[] decrypt(byte[] payload, Key key) throws CryptoException;

	byte[] encrypt(byte[] payload, Key key) throws CryptoException;
}
