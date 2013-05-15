package edu.asu.krypton.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.service.crypto.Hash;

@Component
@Aspect
public class CryptoAspect {
	@Autowired(required=true)
	private Hash hash;
	
	@Before("execution(public void edu.asu.krypton.service.RegistrationService.registerUser(..)) && args(user,..)")
	public void hashPassword(User user){
		user.setPassword(
				getHash().hash(
						user.getPassword()
				)
		);
	}

	public Hash getHash() {
		return hash;
	}

	public void setHash(Hash hash) {
		this.hash = hash;
	}
	
}
