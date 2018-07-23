package com.honeyedoak.cryptoutils;

import com.honeyedoak.cryptoutils.exception.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class AsymmetricCryptoUtilsImpl implements AsymmetricCryptoUtils {

	public AsymmetricCryptoUtilsImpl(String asymetricEncryptionAlgorithm, int asymetricKeySize) {
		this.ASYMETRIC_ENCRYPTION_ALGORITHM = asymetricEncryptionAlgorithm;
		this.ASYMETRIC_KEY_SIZE = asymetricKeySize;
	}

	private final String ASYMETRIC_ENCRYPTION_ALGORITHM;

	private final int ASYMETRIC_KEY_SIZE;


	private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
		return Cipher.getInstance(ASYMETRIC_ENCRYPTION_ALGORITHM);
	}

	@Override
	public KeyPair generateKeyPair() throws CryptoException {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(ASYMETRIC_ENCRYPTION_ALGORITHM);
			keygen.initialize(ASYMETRIC_KEY_SIZE);

			return keygen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		}
	}


	@Override
	public PrivateKey decodePrivateKey(byte[] encodedKey) throws CryptoException {

		try {
			KeyFactory kf = KeyFactory.getInstance(ASYMETRIC_ENCRYPTION_ALGORITHM);
			return kf.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public PublicKey decodePublicKey(byte[] encodedKey) throws CryptoException {
		try {
			KeyFactory kf = KeyFactory.getInstance(ASYMETRIC_ENCRYPTION_ALGORITHM);
			return kf.generatePublic(new X509EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] payload, Key key) throws CryptoException {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, key);

			return cipher.doFinal(payload);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public byte[] encrypt(byte[] payload, Key key) throws CryptoException {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, key);

			return cipher.doFinal(payload);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
			throw new CryptoException(e);
		}
	}
}
