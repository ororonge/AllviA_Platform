package com.platform.authentication.authorization;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		String hex = "";
		if (StringUtils.isEmpty(rawPassword)) {
			return hex;
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			hex = (new HexBinaryAdapter()).marshal(md5.digest(rawPassword.toString().getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hex;
	}
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return StringUtils.equals(rawPassword, encodedPassword);
	}
}