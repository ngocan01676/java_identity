package com.devteria.identity_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Slf4j
class IdentityServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Hello World");
	}
	@Test
	void hash() throws NoSuchAlgorithmException {
		String password = "123456";

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());

		byte[] digest = md.digest();
		String md5Hash = DatatypeConverter.printHexBinary(digest);

		log.info("MD5 round 1: {}", md5Hash);

		md.update(password.getBytes());
		digest = md.digest();
		md5Hash = DatatypeConverter.printHexBinary(digest);

		log.info("MD5 round 2: {}", md5Hash);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

		log.info("BCrypt round 1: {}", passwordEncoder.encode(password));
		log.info("BCrypt round 2: {}", passwordEncoder.encode(password));
	}

}