package edu.asu.krypton.controller.webservices;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.controllers.webservices.CryptoWebService;
import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.service.crypto.aes.AESCryptoSystem;
import edu.asu.krypton.service.crypto.ecc.ECCryptoSystem;
import edu.asu.krypton.service.crypto.ecc.ECKey;
import edu.asu.krypton.service.crypto.ecc.ECPoint;
import edu.asu.krypton.service.crypto.rsa.RSACryptoSystem;
import edu.asu.krypton.service.crypto.rsa.RSAKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CryptoWebServiceTest {
	@Autowired
	private CryptoWebService crypto;
	
	@Test
	public void defaultEndpoint() {
		assert true : "test failed";
		AESCryptoSystem aes = crypto.getAes();
		ECCryptoSystem ecc = crypto.getEcc();
		RSACryptoSystem rsa = crypto.getRsa();
		byte[] result = aes.decryptDiffieHelmanKey(aes.encryptDiffieHelmanKey(new byte[]{1,2,3}));
		System.out.println((Arrays.equals(result, new byte[]{1,2,3})));
		ECKey k = ecc.generateKey();
		byte[] test1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
		result = ecc.decryptWithPrivate(ecc.encryptWithPublic(test1, k.getPublic()), k);
		System.out.println((Arrays.equals(result, test1)));
		result = ecc.applyDiffieHelman(k.getPublic().beta.getx(), k.getPublic().beta.gety());
		System.out.println((Arrays.equals(test1, aes.decryptWithDHSymetricKey(aes.encryptWithDHSymetricKey(test1, result), result))));
		RSAKey rsaKey = rsa.generateKey();
		System.out.println(Arrays.equals(test1, rsa.decryptWithPrivate(rsa.encryptWithPublic(test1, rsaKey.getPublic()), rsaKey)));
		System.out.println(Arrays.equals(test1, rsa.decryptWithPublic(rsa.encryptWithPrivate(test1, rsaKey), rsaKey.getPublic())));
		
		RSAKey clientRSAKey = rsa.generateKey();
		ECKey clientECKey = ecc.generateKey();
		try{
			ecc.createApp("new app 1", clientECKey.beta.getx(), clientECKey.beta.gety());
			rsa.createApp("new app 1", clientRSAKey.n, clientRSAKey.e);
		}catch(CustomRuntimeException ex){
			System.out.println(ex.getMessage());
		}
		
		RSAKey clientRSAKey2 = rsa.generateKey();
		ECKey clientECKey2 = ecc.generateKey();
		try{
			ecc.createApp("new app 2", clientECKey2.beta.getx(), clientECKey2.beta.gety());
			rsa.createApp("new app 2", clientRSAKey2.n, clientRSAKey2.e);
		}catch(CustomRuntimeException ex){
			System.out.println(ex.getMessage());
		}
		
		ECPoint serverPublic = new ECKey(crypto.getEcc().getEc(), crypto.getServerKeyRepository().getServerKey().getSk(), true).getPublic().beta;
		byte[] kk = serverPublic.multiply(clientECKey.sk).getx().toByteArray();
		byte[] DHKey = new byte[16];
		for(int i=0;i<16;i++)
			DHKey[i]=kk[i];
		
		byte[] message = test1;
		MessageDigest md;
		byte[] hash=null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			hash = md.digest(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] encHash = rsa.encryptWithPrivate(hash, clientRSAKey);
		
		byte[] encEncHash = aes.encryptWithDHSymetricKey(encHash, DHKey);
		byte[] encMessage = aes.encryptWithDHSymetricKey(message, DHKey);
		
		DHKey = new byte[16];
		DHKey = ecc.retreiveAesSymmetricSecretkey("new app 1");
		
		byte[] decEncEncHash = aes.decryptWithDHSymetricKey(encEncHash, DHKey);
		byte[] decEncMessage = aes.decryptWithDHSymetricKey(encMessage, DHKey);
		
		System.out.println(rsa.verifySignature(decEncMessage, decEncEncHash, "new app 1"));
		
		
	}
}
