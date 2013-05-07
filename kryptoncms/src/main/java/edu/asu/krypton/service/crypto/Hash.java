package edu.asu.krypton.service.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Hash {
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private org.springframework.security.authentication.encoding.ShaPasswordEncoder shaPasswordEncoder;
	
	@Autowired
	private String hashSalt;
	
	public String hash(String plain){
		return  shaPasswordEncoder.encodePassword(
						plain,
						getHashSalt()
				);
	}

	public org.springframework.security.authentication.encoding.ShaPasswordEncoder getShaPasswordEncoder() {
		return shaPasswordEncoder;
	}

	public void setShaPasswordEncoder(
			org.springframework.security.authentication.encoding.ShaPasswordEncoder shaPasswordEncoder) {
		this.shaPasswordEncoder = shaPasswordEncoder;
	}

	public String getHashSalt() {
		return hashSalt;
	}
	public void setHashSalt(String hashSalt) {
		this.hashSalt = hashSalt;
	}
}