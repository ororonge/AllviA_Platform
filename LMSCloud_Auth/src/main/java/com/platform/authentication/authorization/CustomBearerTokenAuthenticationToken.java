package com.platform.authentication.authorization;

import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

public class CustomBearerTokenAuthenticationToken extends BearerTokenAuthenticationToken {
	private static final long serialVersionUID = 6705804455871950961L;
	public CustomBearerTokenAuthenticationToken(String token) {
		super(token);
	}
}
