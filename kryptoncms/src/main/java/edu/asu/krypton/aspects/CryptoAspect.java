package edu.asu.krypton.aspects;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.krypton.model.persist.db.AppsSecurityInfo;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.ClientsKeysRepository;
import edu.asu.krypton.service.crypto.Hash;
import edu.asu.krypton.service.crypto.aes.AESCryptoSystem;
import edu.asu.krypton.service.crypto.ecc.ECCryptoSystem;
import edu.asu.krypton.service.crypto.rsa.RSACryptoSystem;
import edu.asu.krypton.service.crypto.rsa.RSAKey;

@Component
@Aspect
public class CryptoAspect {
	private final static Logger logger = LoggerFactory.getLogger(CryptoAspect.class);
	
	@Autowired(required=true)
	private Hash hash;
	
	@Autowired(required=true)
	private RSACryptoSystem rsa;
	@Autowired(required=true)
	private AESCryptoSystem aes;
	@Autowired(required=true)
	private ECCryptoSystem ecc;
	@Autowired(required=true)
	private ClientsKeysRepository clientsKeysRepository;
	
	@Before("execution(public void edu.asu.krypton.service.RegistrationService.registerUser(..)) && args(user,..)")
	public void hashPassword(User user){
		user.setPassword(
				getHash().hash(
						user.getPassword()
				)
		);
	}
	
	public void secureWebService(String data){}

	public Hash getHash() {
		return hash;
	}

	public void setHash(Hash hash) {
		this.hash = hash;
	}

	public RSACryptoSystem getRsa() {
		return rsa;
	}

	public void setRsa(RSACryptoSystem rsa) {
		this.rsa = rsa;
	}
	
	public AESCryptoSystem getAes() {
		return aes;
	}

	public void setAes(AESCryptoSystem aes) {
		this.aes = aes;
	}

	public ECCryptoSystem getEcc() {
		return ecc;
	}

	public void setEcc(ECCryptoSystem ecc) {
		this.ecc = ecc;
	}
	
	public ClientsKeysRepository getClientsKeysRepository() {
		return clientsKeysRepository;
	}

	public void setClientsKeysRepository(ClientsKeysRepository clientsKeysRepository) {
		this.clientsKeysRepository = clientsKeysRepository;
	}

//	@Around("execution(* edu.asu.krypton.controllers.webservices.GenericDatabaseWebServiceController.get(..))")
	public String getAround(ProceedingJoinPoint joinPoint){
		System.out.println("reached");
		Object[] args = joinPoint.getArgs();

		HttpServletRequest request = (HttpServletRequest)args[args.length-2];
		HttpServletResponse response = (HttpServletResponse)args[args.length-1];
		
		String appName = request.getParameter("appName");
		String signedAppName = request.getParameter("signedAppName");
		System.out.println("appName:"+appName);
		System.out.println("signedAppName:"+signedAppName);
		if (rsa.verifySignature(appName.getBytes(), signedAppName.getBytes(), appName)) {
			System.out.println("Verified ...");
			AppsSecurityInfo appInfo = clientsKeysRepository.getAppInfo(appName);
			byte[] symmetricKey = appInfo.getDiffieHelmanKey();
			RSAKey clientPublicKey = new RSAKey(appInfo.getN(), appInfo.getE());
			Map parms = request.getParameterMap();
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				if(parameterName.equals("pageSize") || parameterName.equals("pageNo") || parameterName.equals("caseSensitivity")) continue;
				String parameterValue = request.getParameter(parameterName);
				System.out.println("parameterName:"+parameterName);
				System.out.println("parameterValue:"+parameterValue);
				parms.put(parameterName, new String(rsa.decryptWithPublic(aes.decryptWithDHSymetricKey(parameterValue.getBytes(), symmetricKey), clientPublicKey)));
			}
			try {
				String output = (String)joinPoint.proceed(args);
				byte[] encryptedMessage = aes.encryptWithDHSymetricKey(output.getBytes(),symmetricKey);
				byte[] signature = rsa.serverSign(output.getBytes());
				signature = aes.encryptWithDHSymetricKey(signature, symmetricKey);
				response.setHeader("signature", new String(signature));
				return new String(encryptedMessage);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			logger.warn("An Unauthorized Get request has just been Blocked !!");
			return null;
		}
	}
//	@Around("execution(* edu.asu.krypton.controllers.webservices.GenericDatabaseWebServiceController.delete(..))")
	public String deleteAround(ProceedingJoinPoint joinPoint){
		System.out.println("reached");
		Object[] args = joinPoint.getArgs();
		String appName = (String)args[args.length-4];
		String signedAppName = (String)args[args.length-3];
		HttpServletRequest request = (HttpServletRequest)args[args.length-2];
		HttpServletResponse response = (HttpServletResponse)args[args.length-1];
		if (rsa.verifySignature(appName.getBytes(), signedAppName.getBytes(), appName)) {
			AppsSecurityInfo appInfo = clientsKeysRepository.getAppInfo(appName);
			byte[] symmetricKey = appInfo.getDiffieHelmanKey();
			RSAKey clientPublicKey = new RSAKey(appInfo.getN(), appInfo.getE());
			Map parms = request.getParameterMap();
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				if(parameterName.equals("pageSize") || parameterName.equals("pageNo") || parameterName.equals("caseSensitivity")) continue;
				String parameterValue = request.getParameter(parameterName);
				parms.put(parameterName, new String(rsa.decryptWithPublic(aes.decryptWithDHSymetricKey(parameterValue.getBytes(), symmetricKey), clientPublicKey)));
			}
			try {
				String output = (String)joinPoint.proceed(args);
				byte[] encryptedMessage = aes.encryptWithDHSymetricKey(output.getBytes(),symmetricKey);
				byte[] signature = rsa.serverSign(output.getBytes());
				signature = aes.encryptWithDHSymetricKey(signature, symmetricKey);
				response.setHeader("signature", new String(signature));
				return new String(encryptedMessage);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			logger.warn("An Unauthorized Delete request has just been Blocked !!");
			return null;
		}
	}
//	@Around("execution(* edu.asu.krypton.controllers.webservices.GenericDatabaseWebServiceController.post(..))")
	public String postAround(ProceedingJoinPoint joinPoint){
		System.out.println("reached");
		Object[] args = joinPoint.getArgs();
		String appName = (String)args[args.length-4];
		String signedAppName = (String)args[args.length-3];
		HttpServletRequest request = (HttpServletRequest)args[args.length-2];
		HttpServletResponse response = (HttpServletResponse)args[args.length-1];
		if (rsa.verifySignature(appName.getBytes(), signedAppName.getBytes(), appName)) {
			AppsSecurityInfo appInfo = clientsKeysRepository.getAppInfo(appName);
			byte[] symmetricKey = appInfo.getDiffieHelmanKey();
			RSAKey clientPublicKey = new RSAKey(appInfo.getN(), appInfo.getE());
			String requestBody = (String)args[2];
			requestBody = new String(rsa.decryptWithPublic(aes.decryptWithDHSymetricKey(requestBody.getBytes(), symmetricKey), clientPublicKey));
			args[2] = requestBody;
			try {
				String output = (String)joinPoint.proceed(args);
				byte[] encryptedMessage = aes.encryptWithDHSymetricKey(output.getBytes(),symmetricKey);
				byte[] signature = rsa.serverSign(output.getBytes());
				signature = aes.encryptWithDHSymetricKey(signature, symmetricKey);
				response.setHeader("signature", new String(signature));
				return new String(encryptedMessage);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			logger.warn("An Unauthorized Post request has just been Blocked !!");
			return null;
		}
	}

}