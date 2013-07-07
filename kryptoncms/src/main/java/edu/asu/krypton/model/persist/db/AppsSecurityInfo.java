package edu.asu.krypton.model.persist.db;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.form.annotations.Scaffold;
import edu.asu.krypton.model.repository.Repository;
import edu.asu.krypton.service.crypto.KeyStoreManager;
import edu.asu.krypton.service.crypto.ecc.ECCryptoSystem;
import edu.asu.krypton.service.crypto.rsa.RSACryptoSystem;

@Document
@XmlRootElement
@Scaffold
public class AppsSecurityInfo implements DbEntity {

	@Id
	@InputText(readOnly=true)
	private String id;
	
	
	@Indexed(unique = true)
	@InputText
	private String appName;
	// Public Elliptic Curve key
	@InputText
	@Indexed(unique=true)
	private BigInteger publicKey_x;
	@InputText
	@Indexed(unique=true)
	private BigInteger publicKey_y;
	// Symmetric secret key calculated using Elliptic Curve Diffie Hellman Algorithm
	//@Indexed(unique = true)
	//private byte[] diffieHelmanKey;
	// Public RSA key
	@InputText
	@Indexed(unique = true)
	private BigInteger n;
	@InputText
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



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		try {
			ECCryptoSystem.getInstance().createApp(this.appName, this.publicKey_x, this.publicKey_y);
			RSACryptoSystem.getInstance().createApp(this.appName, this.n, this.e);
		} catch (CustomRuntimeException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}		
	}

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOwned(DbEntity owned) {
		// TODO Auto-generated method stub
		
	}
}