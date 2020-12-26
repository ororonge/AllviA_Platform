package com.platform.authentication.security;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.platform.authentication.authorization.CustomPasswordEncoder;
import com.platform.authentication.authorization.CustomTokenEnhancer;

@org.springframework.context.annotation.Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

	@Autowired
	private OAuth2ServerProperties authServerProperties;

    @Autowired
    private CustomPasswordEncoder encoder;
	
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private CustomTokenEnhancer customTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtAccessTokenConverter));

        endpoints
//        		.prefix("/auth") //here
        		.tokenStore(tokenStore)                             //JWT
                .accessTokenConverter(jwtAccessTokenConverter)       //JWT
                .tokenEnhancer(tokenEnhancerChain)               //JWT (filter·Î Ã³¸®)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("eagleeye")
//                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("thisissecret"))
//                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
//                .scopes("webclient", "mobileclient");
		InMemoryClientDetailsServiceBuilder inMemoryBuilder = clients.inMemory();
		for (String clientKey : authServerProperties.getClient().keySet()) {
			OAuthClientProperties client = authServerProperties.getClient().get(clientKey);
			inMemoryBuilder
				.withClient(client.getClientId())
				.secret(encoder.encode(client.getClientSecret()))
				.scopes(ArrayUtils.isEmpty(client.getScopes()) ? new String[] { "openid" } : client.getScopes())
				/*"refresh_token", "password", "client_credentials"*/
				.authorizedGrantTypes(ArrayUtils.isEmpty(client.getAuthorizedGrandTypes()) ? new String[] { "client_credentials" } : client.getAuthorizedGrandTypes())
				.accessTokenValiditySeconds(300);
		}
    }
    
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')");
//		.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
//		final JWTOAuth2AuthenticationFilter filter = new JWTOAuth2AuthenticationFilter(new OrRequestMatcher(
//		//    new AntPathRequestMatcher("/auth/oauth/token"),
//			new AntPathRequestMatcher("/**")
//		));
//		filter.setAuthenticationManager(authenticationManager);
//		security.allowFormAuthenticationForClients().addTokenEndpointAuthenticationFilter(filter);
	}
}
