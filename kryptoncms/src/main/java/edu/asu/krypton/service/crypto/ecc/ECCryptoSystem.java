package edu.asu.krypton.service.crypto.ecc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.exceptions.InsecureCurveException;
import edu.asu.krypton.model.persist.db.AppsSecurityInfo;
import edu.asu.krypton.model.persist.db.ServerKey;
import edu.asu.krypton.model.repository.ClientsKeysRepository;
import edu.asu.krypton.model.repository.ServerKeyRepository;
import edu.asu.krypton.service.crypto.Rand;
import edu.asu.krypton.service.crypto.aes.AESCryptoSystem;

@Service
public class ECCryptoSystem {

	private ServerKeyRepository serverKeyRepository;
	private ClientsKeysRepository clientsKeysRepository;
	private AESCryptoSystem aes;
	
	private static int decNum = 53;

	MessageDigest hash;

	private EllipticCurve ec;
	
	@Autowired(required = true)
	public ECCryptoSystem(ServerKeyRepository serverKeyRepository, ClientsKeysRepository clientsKeysRepository, AESCryptoSystem aes) {
		try {
			setAes(aes);
			setClientsKeysRepository(clientsKeysRepository);
			setServerKeyRepository(serverKeyRepository);
			this.ec = new EllipticCurve(new secp256r1());
			ECKey ecKey = generateKey();
			BigInteger sk = ecKey.sk;
			ServerKey serverKey = serverKeyRepository.getServerKey();
			if(serverKey==null)
				serverKey = new ServerKey();
			serverKey.setSk(sk);
			serverKeyRepository.insertOrUpdateServerKey(serverKey);
		} catch (InsecureCurveException e1) {
			e1.printStackTrace();
		}
		try {
			hash = MessageDigest.getInstance("SHA-1");
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public void updateServerKey() {
		ECKey ecKey = generateKey();
		BigInteger sk = ecKey.sk;
		ServerKey serverKey = serverKeyRepository.getServerKey();
		serverKey.setSk(sk);
		serverKeyRepository.insertOrUpdateServerKey(serverKey);
	}

	public int blockSize() {
		return 20;
	}

	private byte[] encrypt(byte[] input, int numbytes, ECKey ek) {
		byte[] res = new byte[ek.mother.getPCS() + numbytes];
		hash.reset();

		BigInteger rk = new BigInteger(ek.mother.getp().bitLength() + 17,
				Rand.om);
		if (ek.mother.getOrder() != null) {
			rk = rk.mod(ek.mother.getOrder());
		}
		ECPoint gamma = ek.mother.getGenerator().multiply(rk);
		ECPoint sec = ek.beta.multiply(rk);
		System.arraycopy(gamma.compress(), 0, res, 0, ek.mother.getPCS());
		hash.update(sec.getx().toByteArray());
		hash.update(sec.gety().toByteArray());
		byte[] digest = hash.digest();
		for (int j = 0; j < numbytes; j++) {
			res[j + ek.mother.getPCS()] = (byte) (input[j] ^ digest[j]);
		}
		return res;
	}
	
	public byte[] encryptWithPublic(byte[] input, ECKey Key)
	{
	byte[] out = null;
	List<Byte> listOut = new ArrayList<Byte>();
	if (input.length <= 20) {
		out = encrypt(input, input.length, Key);
		return out;
	} else {
		byte[] temp = new byte[20];
		for (int i = 0; i < 20; i++)
			temp[i] = input[i];
		byte[] tempOut = encrypt(temp, temp.length, Key);
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
				tempOut = encrypt(inputExtension, inputExtension.length,
						Key);
				for (int i = 0; i < tempOut.length; i++)
					listOut.add(new Byte(tempOut[i]));
				break;
			} else {
				temp = new byte[20];
				for (int i = 0; i < 20; i++)
					temp[i] = inputExtension[i];
				tempOut = encrypt(temp, temp.length, Key);
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
	
	public byte[] decryptWithPrivate(byte[] input, ECKey Key)
	{
		byte[] out = null;
		List<Byte> listOut = new ArrayList<Byte>();
		if (input.length <= decNum) {
			out = decrypt(input, Key);
			return out;
		} else {
			byte[] temp = new byte[decNum];
			for (int i = 0; i < decNum; i++)
				temp[i] = input[i];
			byte[] tempOut = decrypt(temp, Key);
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
					tempOut = decrypt(inputExtension, Key);
					for (int i = 0; i < tempOut.length; i++)
						listOut.add(new Byte(tempOut[i]));
					break;
				} else {
					temp = new byte[decNum];
					for (int i = 0; i < decNum; i++)
						temp[i] = inputExtension[i];
					tempOut = decrypt(temp, Key);
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


	private byte[] decrypt(byte[] input, ECKey dk) {
		byte[] res = new byte[input.length - dk.mother.getPCS()];
		byte[] gammacom = new byte[dk.mother.getPCS()];
		hash.reset();

		System.arraycopy(input, 0, gammacom, 0, dk.mother.getPCS());
		ECPoint gamma = new ECPoint(gammacom, dk.mother);
		ECPoint sec = gamma.multiply(dk.sk);
		if (sec.isZero()) {
			hash.update(BigInteger.ZERO.toByteArray());
			hash.update(BigInteger.ZERO.toByteArray());
		} else {
			hash.update(sec.getx().toByteArray());
			hash.update(sec.gety().toByteArray());
		}
		byte[] digest = hash.digest();
		for (int j = 0; j < input.length - dk.mother.getPCS(); j++) {
			res[j] = (byte) (input[j + dk.mother.getPCS()] ^ digest[j]);
		}
		return res;
	}

	/**
	 * This method generates a new key for the ECC Crypto-system.
	 * 
	 * @return the new key generated
	 */
	public ECKey generateKey() {
		return new ECKey(ec);
	}

	public String toString() {
		return "ECC - " + ec.toString();
	}

	/**
	 * 
	 * @param appName
	 *            the name of the application
	 * @return returns false if the application name already used or the public key already exists and true if the application is a new one
	 */
	public boolean createApp(String appName, BigInteger publicKey_x, BigInteger publicKey_y) throws CustomRuntimeException{
		if (clientsKeysRepository.getAppInfo(appName) == null) {
			if(clientsKeysRepository.getAppInfoByECCPair(publicKey_x, publicKey_y) != null)
				throw new CustomRuntimeException("The ECC public key entered is already used by another application");
			AppsSecurityInfo appInfo = new AppsSecurityInfo();
			appInfo.setAppName(appName);
			appInfo.setDiffieHelmanKey(aes.encryptDiffieHelmanKey(applyDiffieHelman(publicKey_x, publicKey_y)));
			appInfo.setPublicKey_x(publicKey_x);
			appInfo.setPublicKey_y(publicKey_y);
			clientsKeysRepository.insertOrUpdateAppInfo(appInfo);
			return true;
		}
		throw new CustomRuntimeException("The App name already exists");
	}

	/**
	 * 
	 * @param appName
	 *            the name of the application
	 * @return the symmetric secret key of the application whose name is appName in byte[] format
	 */
	public byte[] retreiveAesSymmetricSecretkey(String appName) {
		AppsSecurityInfo appInfo = clientsKeysRepository.getAppInfo(appName);
		if (appInfo == null)
			return null;
		return aes.decryptDiffieHelmanKey(appInfo.getDiffieHelmanKey());
	}

	public byte[] applyDiffieHelman(BigInteger publicKey_x, BigInteger publicKey_y) {
		byte[] secretKey = new byte[16];
		// diffie helman algorithm
		try{
			ECPoint point = new ECPoint(ec, publicKey_x, publicKey_y);
			ECPoint secretKeyPoint =  point.multiply(serverKeyRepository.getServerKey().getSk());
			byte[] key = secretKeyPoint.getx().toByteArray();
			for(int i=0;i<16;i++)
				secretKey[i]=key[i];
			return secretKey;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public byte[] secureWithAppPublic(byte[] plainData, String appName) {
		try {
			BigInteger publicKey_x = clientsKeysRepository.getAppInfo(appName).getPublicKey_x();
			BigInteger publicKey_y = clientsKeysRepository.getAppInfo(appName).getPublicKey_y();
			ECPoint point = new ECPoint(ec, publicKey_x, publicKey_y);
			ECKey publicKey = new ECKey(ec, point, false);
			return encrypt(plainData, plainData.length, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] decryptWithServerPrivate(byte[] input) {
		BigInteger sk = serverKeyRepository.getServerKey().getSk();
		return decrypt(input, new ECKey(ec, sk, true));
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

	public void setClientsKeysRepository(
			ClientsKeysRepository clientsKeysRepository) {
		this.clientsKeysRepository = clientsKeysRepository;
	}

	public AESCryptoSystem getAes() {
		return aes;
	}

	public void setAes(AESCryptoSystem aes) {
		this.aes = aes;
	}

	public EllipticCurve getEc() {
		return ec;
	}

	public void setEc(EllipticCurve ec) {
		this.ec = ec;
	}	
}