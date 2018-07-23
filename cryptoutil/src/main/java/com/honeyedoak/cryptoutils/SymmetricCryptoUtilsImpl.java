package com.honeyedoak.cryptoutils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;

public class SymmetricCryptoUtilsImpl implements SymmetricCryptoUtils {

	private final String SYMETRIC_ENCRYPTION_ALGORITHM;

	public SymmetricCryptoUtilsImpl(String symetricEncryptionAlgorithm) {
		this.SYMETRIC_ENCRYPTION_ALGORITHM = symetricEncryptionAlgorithm;
	}

	private StandardPBEByteEncryptor byteEncryptor() {
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setProviderName(BouncyCastleProvider.PROVIDER_NAME);
		encryptor.setAlgorithm(SYMETRIC_ENCRYPTION_ALGORITHM);

		return encryptor;
	}

	@Override
	public byte[] encrypt(byte[] payload, String password) {
		StandardPBEByteEncryptor encryptor = byteEncryptor();
		encryptor.setPassword(password);
		return encryptor.encrypt(payload);
	}

	@Override
	public byte[] encrypt(byte[] payload, char[] password) {
		StandardPBEByteEncryptor encryptor = byteEncryptor();
		encryptor.setPasswordCharArray(password);
		return encryptor.encrypt(payload);
	}

	@Override
	public byte[] decrypt(byte[] payload, String password) {
		StandardPBEByteEncryptor decryptor = byteEncryptor();
		decryptor.setPassword(password);
		return decryptor.decrypt(payload);
	}

	@Override
	public byte[] decrypt(byte[] payload, char[] password) {
		StandardPBEByteEncryptor decryptor = byteEncryptor();
		decryptor.setPasswordCharArray(password);
		return decryptor.decrypt(payload);
	}

}
