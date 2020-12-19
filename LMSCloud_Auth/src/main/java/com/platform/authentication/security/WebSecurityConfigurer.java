package com.platform.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring()
        .antMatchers(HttpMethod.OPTIONS);
    }
	
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("user").password("password").roles("ADMIN")
                .and()
                .withUser("admin").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
    	security
		.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/home", "/js/**", "/css/**", "/img/**", "/favicon.ico").permitAll()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login").permitAll()
//			.successHandler(authenticationHandler())
//			.failureHandler(authenticationHandler())
		.and()
			.logout().permitAll()
//		.and()		// 일반 페이지에서도 401에러가 발생하여 주석처리
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//		.and()		// SavedRequest 값이 정상처리 안되어 주석처리
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.httpBasic();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    @Autowired
    public UserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(this.clientDetailsService));
        handler.setClientDetailsService(this.clientDetailsService);

        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);

        return store;
    }
}
