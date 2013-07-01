package edu.asu.krypton.model.persist.db;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;
import edu.asu.krypton.service.crypto.KeyStoreManager;
/**
 * @author Mostafa
 *
 */
@Document
@XmlRootElement
public class ServerKey implements DbEntity {

	@Id
	private String id;
	// ECC key
	//private BigInteger sk;
	// RSA key
	private BigInteger n;
	private BigInteger e;
	//private BigInteger d;
	
	public ServerKey(){
		super();
	}

	public BigInteger getSk() {
		return new BigInteger(KeyStoreManager.retreiveSecretKey("server_sk"));
	}

	public void setSk(BigInteger sk) {
		KeyStoreManager.saveSecretKey("server_sk", sk.toByteArray(), "ECC");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BigInteger getD() {
		return new BigInteger(KeyStoreManager.retreiveSecretKey("sever_d"));
	}

	public void setD(BigInteger d) {
		KeyStoreManager.saveSecretKey("sever_d", d.toByteArray(), "RSA");
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

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}
}