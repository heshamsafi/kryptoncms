package edu.asu.krypton.service.crypto.rsa;

import java.math.BigInteger;

import edu.asu.krypton.service.crypto.Rand;

public class RSAKey {
	public BigInteger n;
	protected BigInteger phin;
	protected BigInteger p;
	protected BigInteger q;
	public BigInteger e;
	public BigInteger d;
	protected boolean secret;

	public boolean isPublic() {
		return (!secret);
	}

	public RSAKey(int bits) {
		secret = true;
		p = new BigInteger(bits / 2, 500, Rand.om);
		q = new BigInteger(bits / 2, 500, Rand.om);
		n = p.multiply(q);
		phin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger(bits, Rand.om);
		while (!e.gcd(phin).equals(BigInteger.ONE)) {
			e = new BigInteger(bits, Rand.om);
		}
		d = e.modInverse(phin);
	}

	public RSAKey() {
	}

	public RSAKey(BigInteger n, BigInteger d, boolean secret) {
		this.secret = secret;
		this.d = d;
		this.n = n;
	}
	
	public RSAKey(BigInteger n, BigInteger e) {
		secret = false;
		this.e = e;
		this.n = n;
	}
	
	/** Turns this key into a public key (does nothing if this key is public) */
	public RSAKey getPublic() {
		RSAKey temp = new RSAKey();
		temp.n = n;
		temp.e = e;
		temp.secret = false;
		return temp;
	}
}