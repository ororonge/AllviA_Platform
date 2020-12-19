package com.platform.authentication.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties ( prefix = "authorization-server" )
public class AuthServerProperties {
	private final Map<String, OAuthClientProperties> client = new HashMap<> ();
	public Map<String, OAuthClientProperties> getClient () {
        return client;
    }
}
