package com.platform.authentication.security;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.platform.authentication.authorization.PlatformPasswordEncoder;

@Configuration
public class OAuth2Config implements AuthorizationServerConfigurer {

	@Autowired
	private AuthServerProperties authServerProperties;

	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
	
    @Autowired
    private PlatformPasswordEncoder encoder;
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//	        clients.inMemory()
//	        .withClient("eagleeye")
//	        .secret(encoder.encode("thisissecret"))
//	        .authorizedGrantTypes("refresh_token", "password", "client_credentials")
//	        .scopes("webclient", "mobileclient");
		InMemoryClientDetailsServiceBuilder inMemoryBuilder = clients.inMemory();
		for (String clientKey : authServerProperties.getClient().keySet()) {
			OAuthClientProperties client = authServerProperties.getClient().get(clientKey);
			inMemoryBuilder
				.withClient(client.getClientId())
				.secret(encoder.encode(client.getClientSecret()))
				.scopes(ArrayUtils.isEmpty(client.getScopes()) ? new String[] { "openid" } : client.getScopes())
				.authorizedGrantTypes(ArrayUtils.isEmpty(client.getAuthorizedGrandTypes()) ? new String[] { "client_credentials" } : client.getAuthorizedGrandTypes()); // "refresh_token", "password", "client_credentials"
		}
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
	}
}
