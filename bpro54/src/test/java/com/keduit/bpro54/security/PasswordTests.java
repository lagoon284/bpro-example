package com.keduit.bpro54.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void testEncode() {	// 인코딩 할때마다 암호화 값이 달라짐.
		String password = "1111";
		String enPw = passwordEncoder.encode(password);
		System.out.println("enpw : " + enPw);
		
		boolean matchResult = passwordEncoder.matches(password, enPw);
		
		System.out.println("match result : " + matchResult);
	}

}
