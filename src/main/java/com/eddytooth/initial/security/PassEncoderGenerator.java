package com.eddytooth.initial.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassEncoderGenerator {

	public static void main(String[] args) {
		PasswordEncoder pasenc = new BCryptPasswordEncoder();
		System.out.println(pasenc.encode("pass"));
	}

}
