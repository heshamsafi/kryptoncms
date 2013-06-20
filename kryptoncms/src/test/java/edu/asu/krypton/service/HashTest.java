package edu.asu.krypton.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.service.crypto.Hash;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class HashTest {
	
	@Autowired(required=true)
	private Hash hash;

	public Hash getHash() {
		return hash;
	}

	public void setHash(Hash hash) {
		this.hash = hash;
	}
	
	@Test
	public void checkHash1(){
		String pwd = "admin";
		//value from db
		String expectedPwd = "3a8cd02ce4ac6182119d3a31b80c2b8f5f60393f";
		
		String hashedPwd = hash.hash(pwd);
		
		System.out.println("Hash Test - print");
		System.out.println("hash salt = " + hash.getHashSalt());
		System.out.println("hashedPwd = " + hashedPwd);
		System.out.println("expectedPwd = " + expectedPwd);
		if(expectedPwd.equals(hashedPwd)){
			System.out.println("expectedPwd = hashedPwd");
		}
		Assert.assertEquals(expectedPwd, hashedPwd);
		
	}
	
//	@Test
//	public void checkHash2(){
//		String pwd = "hello";
//		hash.setHashSalt("QxLUF1bgIAdeQX");
//		String expectedPwd = "9e209040c863f84a31e719795b2577523954739fe5ed3b58a75cff2127075ed1";
//
//		String hashedPwd = hash.hash(pwd);
//		System.out.println("Hash Test - print");
//		System.out.println("hash salt = " + hash.getHashSalt());
//		System.out.println("hashedPwd = " + hashedPwd);
//		System.out.println("expectedPwd = " + expectedPwd);
//		if(expectedPwd.equals(hashedPwd)){
//			System.out.println("expectedPwd = hashedPwd");
//		}
//		
//		Assert.assertEquals(expectedPwd, hashedPwd);
//	}
//	
//	@Test
//	public void checkHash3(){
//		String pwd = "hello";
//		hash.setHashSalt("bv5PehSMfV11Cd");
//		String expectedPwd = "6ca302468b30fa6b0c67d8830f6bcd5fcf84b027";
//
//		String hashedPwd = hash.hash(pwd);
//		System.out.println("Hash Test - print");
//		System.out.println("hash salt = " + hash.getHashSalt());
//		System.out.println("hashedPwd = " + hashedPwd);
//		System.out.println("expectedPwd = " + expectedPwd);
//		if(expectedPwd.equals(hashedPwd)){
//			System.out.println("expectedPwd = hashedPwd");
//		}
//		
//		Assert.assertEquals(expectedPwd, hashedPwd);
//	}
//
//	@Test
//	public void checkHash4(){
//		String pwd = "hello";
//		hash.setHashSalt("YYLmfY6IehjZMQ");
//		String expectedPwd = "a49670c3c18b9e079b9cfaf51634f563dc8ae3070db2c4a8544305df1b60f007";
//
//		String hashedPwd = hash.hash(pwd);
//		System.out.println("Hash Test - print");
//		System.out.println("hash salt = " + hash.getHashSalt());
//		System.out.println("hashedPwd = " + hashedPwd);
//		System.out.println("expectedPwd = " + expectedPwd);
//		if(expectedPwd.equals(hashedPwd)){
//			System.out.println("expectedPwd = hashedPwd");
//		}
//		
//		Assert.assertEquals(expectedPwd, hashedPwd);
//	}
//
//	@Test
//	public void checkHash5(){
//		String pwd = "easypassword";
//		hash.setHashSalt("f#@V)Hu^%Hgfds");
//		String expectedPwd = "cd56a16759623378628c0d9336af69b74d9d71a5";
//
//		String hashedPwd = hash.hash(pwd);
//		System.out.println("Hash Test - print");
//		System.out.println("hash salt = " + hash.getHashSalt());
//		System.out.println("hashedPwd = " + hashedPwd);
//		System.out.println("expectedPwd = " + expectedPwd);
//		if(expectedPwd.equals(hashedPwd)){
//			System.out.println("expectedPwd = hashedPwd");
//		}
//		
//		Assert.assertEquals(expectedPwd, hashedPwd);
//	}

	@Test
	public void checkHash6(){
		String pwd = "hello";
		hash.setHashSalt("");
		String expectedPwd = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d";

		String hashedPwd = hash.hash(pwd);
		System.out.println("Hash Test - print");
		System.out.println("hash salt = " + hash.getHashSalt());
		System.out.println("hashedPwd = " + hashedPwd);
		System.out.println("expectedPwd = " + expectedPwd);
		if(expectedPwd.equals(hashedPwd)){
			System.out.println("expectedPwd = hashedPwd");
		}
		
		Assert.assertEquals(expectedPwd, hashedPwd);
	}

}
