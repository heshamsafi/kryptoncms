package edu.asu.krypton.model.persist.db;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;
import edu.asu.krypton.service.crypto.KeyStoreManager;

@Document
@XmlRootElement
public class AppsSecurityInfo implements DbEntity {

	@Id
	private String appId;
	@Indexed(unique = true)
	private String appName;
	// Public Elliptic Curve key
	@Indexed(unique=true)
	private BigInteger publicKey_x;
	@Indexed(unique=true)
	private BigInteger publicKey_y;
	// Symmetric secret key calculated using Elliptic Curve Diffie Hellman Algorithm
	//@Indexed(unique = true)
	//private byte[] diffieHelmanKey;
	// Public RSA key
	@Indexed(unique = true)
	private BigInteger n;
	@Indexed(unique = true)
	private BigInteger e;

	public AppsSecurityInfo() {
		super();
	}

	public byte[] getDiffieHelmanKey() {
		return KeyStoreManager.retreiveSecretKey(getAppName());
	}

	public void setDiffieHelmanKey(byte[] diffieHelmanKey) {
		KeyStoreManager.saveSecretKey(getAppName(), diffieHelmanKey, "AES");
	}

	public void setPublicKey_x(BigInteger publicKey_x) {
		this.publicKey_x = publicKey_x;
	}
	
	public void setPublicKey_y(BigInteger publicKey_y) {
		this.publicKey_y = publicKey_y;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public BigInteger getPublicKey_x() {
		return publicKey_x;
	}
	
	public BigInteger getPublicKey_y() {
		return publicKey_y;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
}