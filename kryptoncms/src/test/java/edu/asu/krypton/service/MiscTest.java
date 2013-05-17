package edu.asu.krypton.service;

import org.junit.Test;

public class MiscTest {

	@Test
	public void test() {
		String str = "adadadadadadadadadadadadadad";
		String str2 = "none";
		int cutoff = 10;
		if(cutoff < str.length())
			str2 = str.substring(0, cutoff);
		System.out.println(str2);
	}

}
