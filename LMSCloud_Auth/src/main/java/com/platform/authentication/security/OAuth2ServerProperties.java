package com.platform.authentication.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties ( prefix = "authorization-server" )
public class OAuth2ServerProperties {
	private final Map<String, OAuthClientProperties> client = new HashMap<> ();
	public Map<String, OAuthClientProperties> getClient () {
        return client;
    }
}
