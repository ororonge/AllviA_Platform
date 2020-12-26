package com.platform.authentication.security;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.platform.authentication.authorization.CustomPasswordEncoder;
import com.platform.authentication.authorization.CustomTokenEnhancer;

//@org.springframework.context.annotation.Configuration/*JWTOAuth2Config 사용*/
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private OAuth2ServerProperties authServerProperties;

	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
	
    @Autowired
    private CustomPasswordEncoder encoder;
    
    @Autowired
    private ClientDetailsService clientDetailsService;
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
    @Autowired
    private CustomTokenEnhancer customTokenEnhancer;
    
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
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
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenEnhancer(customTokenEnhancer)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore(redisConnectionFactory))
                .userApprovalHandler(userApprovalHandler())
//                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService);
    }
    
    @Bean
    public UserApprovalHandler userApprovalHandler() {
        ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
        userApprovalHandler.setApprovalStore(approvalStore());
        userApprovalHandler.setClientDetailsService(clientDetailsService);
        userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        return userApprovalHandler;
    }
    
	@Bean(name="customRedisTokenStore")
	public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
	    return new RedisTokenStore(redisConnectionFactory);
	}
	
    @Bean
    public ApprovalStore approvalStore() {
        return new InMemoryApprovalStore();
    }

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 여기 주석 풀면 filter 걸림 filter 말고 provider로 구현함
//		final OAuth2AuthenticationFilter filter = new OAuth2AuthenticationFilter(new OrRequestMatcher(
////		    new AntPathRequestMatcher("/auth/oauth/token"),
//	    	new AntPathRequestMatcher("/**")
//		));
//		filter.setAuthenticationManager(authenticationManager);
//		security.allowFormAuthenticationForClients().addTokenEndpointAuthenticationFilter(filter);
	}
	
//  @Bean
//  public JwtAccessTokenConverter accessTokenConverter() {
//      final RsaSigner signer = new RsaSigner(KeyConfig.getSignerKey());
//      JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
//          private JsonParser objectMapper = JsonParserFactory.create();
//          @Override
//          protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//              String content;
//              try {
//                  content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
//              } catch (Exception ex) {
//                  throw new IllegalStateException("Cannot convert access token to JSON", ex);
//              }
//              Map<String, String> headers = new HashMap<>();
//              headers.put("kid", KeyConfig.VERIFIER_KEY_ID);
//              return JwtHelper.encode(content, signer, headers).getEncoded();
//          }
//      };
//      converter.setSigner(signer);
//      converter.setVerifier(new RsaVerifier(KeyConfig.getVerifierKey()));
//      return converter;
//  }
	
//  @Bean
//  public JWKSet jwkSet() {
//      RSAKey.Builder builder = new RSAKey.Builder(KeyConfig.getVerifierKey())
//              .keyUse(KeyUse.SIGNATURE)
//              .algorithm(JWSAlgorithm.RS256)
//              .keyID(KeyConfig.VERIFIER_KEY_ID);
//      return new JWKSet(builder.build());
//  }
}
