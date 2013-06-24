package edu.asu.krypton.service.crypto.rsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.AppsSecurityInfo;
import edu.asu.krypton.model.persist.db.ServerKey;
import edu.asu.krypton.model.repository.ClientsKeysRepository;
import edu.asu.krypton.model.repository.ServerKeyRepository;
import edu.asu.krypton.service.crypto.Rand;

@Service
public class RSACryptoSystem {
	MessageDigest hash;
	
	private ServerKeyRepository serverKeyRepository;
	private ClientsKeysRepository clientsKeysRepository;
	
	private static int decNum = 150;

	@Autowired(required=true)
	public RSACryptoSystem(ServerKeyRepository serverKeyRepository, ClientsKeysRepository clientsKeysRepository) {
		try {
			setServerKeyRepository(serverKeyRepository);
			setClientsKeysRepository(clientsKeysRepository);
			RSAKey k = generateKey();
			ServerKey serverKey = serverKeyRepository.getServerKey();
			if(serverKey==null)
				serverKey = new ServerKey();
			serverKey.setD(k.d);
			serverKey.setE(k.e);
			serverKey.setN(k.n);
			serverKeyRepository.insertOrUpdateServerKey(serverKey);
			hash = MessageDigest.getInstance("SHA-1");
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println("RSACryptoSystem: THIS CANNOT HAPPEN\n" + e);
			System.exit(0);
		}
	}
	
	public void updateServerKey() {
		RSAKey k = generateKey();
		ServerKey serverKey = serverKeyRepository.getServerKey();
		serverKey.setD(k.d);
		serverKey.setE(k.e);
		serverKey.setN(k.n);
		serverKeyRepository.insertOrUpdateServerKey(serverKey);
	}

	public int blockSize() {
		return 20;
	}

	public boolean createApp(String appName, BigInteger n, BigInteger e) throws CustomRuntimeException{
		if ( (clientsKeysRepository.getAppInfo(appName) == null) || (clientsKeysRepository.getAppInfoByRSAPair(n, e) != null) ){
			if(clientsKeysRepository.getAppInfo(appName) != null)
				clientsKeysRepository.delete(clientsKeysRepository.getAppInfo(appName));
			throw new CustomRuntimeException("The RSA public key entered is already used by another application");
		}
		AppsSecurityInfo appInfo = clientsKeysRepository.getAppInfo(appName);
		appInfo.setE(e);
		appInfo.setN(n);
		clientsKeysRepository.insertOrUpdateAppInfo(appInfo);
		return true;
	}
	
	private byte[] encryptWithPrivate(byte[] input, int numbytes, RSAKey k) {
		hash.reset();
		BigInteger hashelem = new BigInteger(k.n.bitLength() + 17, Rand.om)
				.mod(k.n);
		byte[] cryptelm = hashelem.modPow(k.d, k.n).toByteArray();
		byte[] res = new byte[cryptelm.length + numbytes + 2];
		res[0] = (byte) ((cryptelm.length) >> 8);
		res[1] = (byte) cryptelm.length;
		System.arraycopy(cryptelm, 0, res, 2, cryptelm.length);
		hash.update(hashelem.toByteArray());
		byte[] digest = hash.digest();
		for (int j = 0; j < numbytes; j++) {
			res[cryptelm.length + 2 + j] = (byte) (input[j] ^ digest[j]);
		}
		return res;
	}

	private byte[] encryptWithPublic(byte[] input, int numbytes, RSAKey k) {
		hash.reset();
		BigInteger hashelem = new BigInteger(k.n.bitLength() + 17, Rand.om)
				.mod(k.n);
		byte[] cryptelm = hashelem.modPow(k.e, k.n).toByteArray();
		byte[] res = new byte[cryptelm.length + numbytes + 2];
		res[0] = (byte) ((cryptelm.length) >> 8);
		res[1] = (byte) cryptelm.length;
		System.arraycopy(cryptelm, 0, res, 2, cryptelm.length);
		hash.update(hashelem.toByteArray());
		byte[] digest = hash.digest();
		for (int j = 0; j < numbytes; j++) {
			res[cryptelm.length + 2 + j] = (byte) (input[j] ^ digest[j]);
		}
		return res;
	}

	
	private byte[] decryptWithPublicKey(byte[] input, RSAKey k) {
		byte[] cryptelm = new byte[((input[0] & 255) << 8) + input[1] & 255];
		byte[] res = new byte[input.length - 2 - cryptelm.length];
		System.arraycopy(input, 2, cryptelm, 0, cryptelm.length);
		hash.reset();
		hash.update(new BigInteger(cryptelm).modPow(k.e, k.n).toByteArray());
		byte[] digest = hash.digest();
		for (int j = 0; j < res.length; j++) {
			res[j] = (byte) (input[cryptelm.length + 2 + j] ^ digest[j]);
		}
		return res;
	}

	private byte[] decryptWithPrivateKey(byte[] input, RSAKey k) {
		byte[] cryptelm = new byte[((input[0] & 255) << 8) + input[1] & 255];
		byte[] res = new byte[input.length - 2 - cryptelm.length];
		System.arraycopy(input, 2, cryptelm, 0, cryptelm.length);
		hash.reset();
		hash.update(new BigInteger(cryptelm).modPow(k.d, k.n).toByteArray());
		byte[] digest = hash.digest();
		for (int j = 0; j < res.length; j++) {
			res[j] = (byte) (input[cryptelm.length + 2 + j] ^ digest[j]);
		}
		return res;
	}
	
	public byte[] encryptWithPublic(byte[] input, RSAKey Key)
	{
	byte[] out = null;
	List<Byte> listOut = new ArrayList<Byte>();
	if (input.length <= 20) {
		out = encryptWithPublic(input, input.length, Key);
		return out;
	} else {
		byte[] temp = new byte[20];
		for (int i = 0; i < 20; i++)
			temp[i] = input[i];
		byte[] tempOut = encryptWithPublic(temp, temp.length, Key);
		decNum = tempOut.length;
		byte[] inputExtension = new byte[input.length - 20];
		int j = 0;
		for (int i = 20; i < input.length; i++) {
			inputExtension[j++] = input[i];
		}
		j = 0;
		for (int i = 0; i < tempOut.length; i++)
			listOut.add(new Byte(tempOut[i]));
		do {
			if (inputExtension.length <= 20) {
				tempOut = encryptWithPublic(inputExtension, inputExtension.length,
						Key);
				for (int i = 0; i < tempOut.length; i++)
					listOut.add(new Byte(tempOut[i]));
				break;
			} else {
				temp = new byte[20];
				for (int i = 0; i < 20; i++)
					temp[i] = inputExtension[i];
				tempOut = encryptWithPublic(temp, temp.length, Key);
				for (int i = 0; i < tempOut.length; i++)
					listOut.add(new Byte(tempOut[i]));
				int length = inputExtension.length;
				temp = new byte[length - 20];
				j = 0;
				for (int i = 20; i < length; i++) {
					temp[j++] = inputExtension[i];
				}
				inputExtension = temp;
			}
		} while (true);
	}
	out = new byte[listOut.size()];
	for (int i = 0; i < listOut.size(); i++)
		out[i] = listOut.get(i).byteValue();
	return out;
	}
	
	public byte[] decryptWithPrivate(byte[] input, RSAKey Key)
	{
		byte[] out = null;
		List<Byte> listOut = new ArrayList<Byte>();
		if (input.length <= decNum) {
			out = decryptWithPrivateKey(input, Key);
			return out;
		} else {
			byte[] temp = new byte[decNum];
			for (int i = 0; i < decNum; i++)
				temp[i] = input[i];
			byte[] tempOut = decryptWithPrivateKey(temp, Key);
			byte[] inputExtension = new byte[input.length - decNum];
			int j = 0;
			for (int i = decNum; i < input.length; i++) {
				inputExtension[j++] = input[i];
			}
			j = 0;
			for (int i = 0; i < tempOut.length; i++)
				listOut.add(new Byte(tempOut[i]));
			do {
				if (inputExtension.length <= decNum) {
					tempOut = decryptWithPrivateKey(inputExtension, Key);
					for (int i = 0; i < tempOut.length; i++)
						listOut.add(new Byte(tempOut[i]));
					break;
				} else {
					temp = new byte[decNum];
					for (int i = 0; i < decNum; i++)
						temp[i] = inputExtension[i];
					tempOut = decryptWithPrivateKey(temp, Key);
					for (int i = 0; i < tempOut.length; i++)
						listOut.add(new Byte(tempOut[i]));
					int length = inputExtension.length;
					temp = new byte[length - decNum];
					j = 0;
					for (int i = decNum; i < length; i++) {
						temp[j++] = inputExtension[i];
					}
					inputExtension = temp;
				}
			} while (true);
		}
		out = new byte[listOut.size()];
		for (int i = 0; i < listOut.size(); i++)
			out[i] = listOut.get(i).byteValue();
		return out;
	}

	public byte[] encryptWithPrivate(byte[] input, RSAKey Key)
	{
	byte[] out = null;
	List<Byte> listOut = new ArrayList<Byte>();
	if (input.length <= 20) {
		out = encryptWithPrivate(input, input.length, Key);
		return out;
	} else {
		byte[] temp = new byte[20];
		for (int i = 0; i < 20; i++)
			temp[i] = input[i];
		byte[] tempOut = encryptWithPrivate(temp, temp.length, Key);
		decNum = tempOut.length;
		byte[] inputExtension = new byte[input.length - 20];
		int j = 0;
		for (int i = 20; i < input.length; i++) {
			inputExtension[j++] = input[i];
		}
		j = 0;
		for (int i = 0; i < tempOut.length; i++)
			listOut.add(new Byte(tempOut[i]));
		do {
			if (inputExtension.length <= 20) {
				tempOut = encryptWithPrivate(inputExtension, inputExtension.length,
						Key);
				for (int i = 0; i < tempOut.length; i++)
					listOut.add(new Byte(tempOut[i]));
				break;
			} else {
				temp = new byte[20];
				for (int i = 0; i < 20; i++)
					temp[i] = inputExtension[i];
				tempOut = encryptWithPrivate(temp, temp.length, Key);
				for (int i = 0; i < tempOut.length; i++)
					listOut.add(new Byte(tempOut[i]));
				int length = inputExtension.length;
				temp = new byte[length - 20];
				j = 0;
				for (int i = 20; i < length; i++) {
					temp[j++] = inputExtension[i];
				}
				inputExtension = temp;
			}
		} while (true);
	}
	out = new byte[listOut.size()];
	for (int i = 0; i < listOut.size(); i++)
		out[i] = listOut.get(i).byteValue();
	return out;
	}
	
	public byte[] decryptWithPublic(byte[] input, RSAKey Key)
	{
		byte[] out = null;
		List<Byte> listOut = new ArrayList<Byte>();
		if (input.length <= decNum) {
			out = decryptWithPublicKey(input, Key);
			return out;
		} else {
			byte[] temp = new byte[decNum];
			for (int i = 0; i < decNum; i++)
				temp[i] = input[i];
			byte[] tempOut = decryptWithPublicKey(temp, Key);
			byte[] inputExtension = new byte[input.length - decNum];
			int j = 0;
			for (int i = decNum; i < input.length; i++) {
				inputExtension[j++] = input[i];
			}
			j = 0;
			for (int i = 0; i < tempOut.length; i++)
				listOut.add(new Byte(tempOut[i]));
			do {
				if (inputExtension.length <= decNum) {
					tempOut = decryptWithPublicKey(inputExtension, Key);
					for (int i = 0; i < tempOut.length; i++)
						listOut.add(new Byte(tempOut[i]));
					break;
				} else {
					temp = new byte[decNum];
					for (int i = 0; i < decNum; i++)
						temp[i] = inputExtension[i];
					tempOut = decryptWithPublicKey(temp, Key);
					for (int i = 0; i < tempOut.length; i++)
						listOut.add(new Byte(tempOut[i]));
					int length = inputExtension.length;
					temp = new byte[length - decNum];
					j = 0;
					for (int i = decNum; i < length; i++) {
						temp[j++] = inputExtension[i];
					}
					inputExtension = temp;
				}
			} while (true);
		}
		out = new byte[listOut.size()];
		for (int i = 0; i < listOut.size(); i++)
			out[i] = listOut.get(i).byteValue();
		return out;
	}
	
	/**
	 * 
	 * @param message
	 *            the original message.
	 * @return the message after applying hashing then encryption by private key on it.
	 */
	public byte[] serverSign(byte[] message) {
		try {
			BigInteger d = serverKeyRepository.getServerKey().getD();
			BigInteger n = serverKeyRepository.getServerKey().getN();
			RSAKey privateKey = new RSAKey(n, d, true);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(message);
			return encryptWithPrivate(hash, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean verifySignature(byte[] originalMessage,
			byte[] hashedMessage, String appName) {
		try {
			BigInteger e = clientsKeysRepository.getAppInfo(appName).getE();
			BigInteger n = clientsKeysRepository.getAppInfo(appName).getN();
			RSAKey publicKey = new RSAKey(n, e);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(originalMessage);
			byte[] decHash = decryptWithPublic(hashedMessage, publicKey);
			if (Arrays.equals(hash, decHash))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * This method generates a new key for the cryptosystem.
	 * 
	 * @return the new key generated
	 */
	public RSAKey generateKey() {
		return new RSAKey(1024);
	}

	public String toString() {
		return "RSA-Cryptosystem";
	}

	public ServerKeyRepository getServerKeyRepository() {
		return serverKeyRepository;
	}

	public void setServerKeyRepository(ServerKeyRepository serverKeyRepository) {
		this.serverKeyRepository = serverKeyRepository;
	}

	public ClientsKeysRepository getClientsKeysRepository() {
		return clientsKeysRepository;
	}

	public void setClientsKeysRepository(ClientsKeysRepository clientsKeysRepository) {
		this.clientsKeysRepository = clientsKeysRepository;
	}
}