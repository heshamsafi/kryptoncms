package edu.asu.krypton.service.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;

import javax.crypto.spec.SecretKeySpec;

public class KeyStoreManager {
	
	private static boolean isAlreadyCreated = false;
	/**
	 * the password of the key store created, the default is 'kryptonCMSPassword'
	 */
	private static char[] passWord = new String("kryptonCMSPassword").toCharArray();
	/**
	 * the full path of the key store
	 */
	private static String fullPath = "D:\\kryptonKeyStore";

	private static void createKeyStore() {
		try {
			KeyStore ks = KeyStore.getInstance("JCEKS");
			ks.load(null, passWord);
			// Store away the key store.
			File file = new File(System.getProperty("user.home")+File.separator+"key-store");
			file.mkdirs();
			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+File.separator+"kryptonKeyStore");
			ks.store(fos, passWord);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param secretKeyName
	 *            name of the secret key , it must be unique for a certain
	 *            secret key , in other words it acts as the identifier of the
	 *            secret key
	 * @param secretKey
	 *            the secret key to be saved
	 * @param algorithm
	 *            the security algorithm that uses that secret key , for
	 *            example: AES, DES, RSA, ECC and so on
	 */
	public static void saveSecretKey(String secretKeyName, byte[] secretKey, String algorithm) {
		try {
			if(!isAlreadyCreated){
				createKeyStore();
				isAlreadyCreated = true;
			}
			File file = new File(System.getProperty("user.home")+File.separator+"key-store");
			file.mkdirs();
			KeyStore ks = KeyStore.getInstance("JCEKS");
			// get user password and file input stream
			java.io.FileInputStream fis = null;
			fis = new java.io.FileInputStream(file.getAbsolutePath()+File.separator+"kryptonKeyStore");
			ks.load(fis, passWord);
			javax.crypto.SecretKey mySecretKey = new SecretKeySpec(secretKey,
					algorithm);
			KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(
					mySecretKey);
			ks.setEntry(secretKeyName, skEntry,
					new KeyStore.PasswordProtection(passWord));
			// store away the key store
			java.io.FileOutputStream fos = null;
			try {
				fos = new java.io.FileOutputStream(file.getAbsolutePath()+File.separator+"kryptonKeyStore");
				ks.store(fos, passWord);
			} finally {
				if (fos != null) {
					fos.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param secretKeyName
	 *            name of the secret key , it must be unique for a certain
	 *            secret key , in other words it acts as the identitfier of the
	 *            secret key
	 * @return
	 * 			  the secret key in a byte[] format
	 */
	public static byte[] retreiveSecretKey(String secretKeyName) {
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS");
			java.io.FileInputStream fis = new java.io.FileInputStream(fullPath);
			ks.load(fis, passWord);
			KeyStore.SecretKeyEntry skEntry = (KeyStore.SecretKeyEntry)ks.getEntry(secretKeyName, new KeyStore.PasswordProtection(passWord));
	        return skEntry.getSecretKey().getEncoded();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}