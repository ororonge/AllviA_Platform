package com.platform.authentication.authorization;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -7221947049086733413L;
	private String authority;
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String getAuthority() {
		return authority;
	}
}