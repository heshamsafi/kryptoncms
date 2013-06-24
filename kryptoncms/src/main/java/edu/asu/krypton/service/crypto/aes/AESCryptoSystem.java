package edu.asu.krypton.service.crypto.aes;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AESCryptoSystem {

	private Key key;

	public AESCryptoSystem() {
		byte[] k = generateAESKey();
		key = new SecretKeySpec(k, "AES");
	}

	public void updateServerKey() {
		byte[] k = generateAESKey();
		key = new SecretKeySpec(k, "AES");
	}

	public byte[] encrypt(byte[] Data, Key key) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data);
		return encVal;
	}

	public byte[] decrypt(byte[] encryptedData, Key key) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decValue = c.doFinal(encryptedData);
		return decValue;
	}

	public byte[] decryptDiffieHelmanKey(byte[] decDiffieHelmanKey) {
		// decrypt with the generated key
		try {
			return decrypt(decDiffieHelmanKey, key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] encryptDiffieHelmanKey(byte[] diffieHelmanKey) {
		// encrypt with the generated key
		try {
			return encrypt(diffieHelmanKey, key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] encryptWithDHSymetricKey(byte[] input, byte[] DHKey){
		try {
			Key k = new SecretKeySpec(DHKey, "AES");
			return encrypt(input, k);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] decryptWithDHSymetricKey(byte[] input, byte[] DHKey){
		try {
			Key k = new SecretKeySpec(DHKey, "AES");
			return decrypt(input, k);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] generateAESKey() {
		byte[] k = new byte[16];
		for (int i = 0; i < 16; i++) {
			k[i] = 'A';
			k[i] += Math.random() * (1 + 'Y' - 'A');
		}
		return k;
	}
}