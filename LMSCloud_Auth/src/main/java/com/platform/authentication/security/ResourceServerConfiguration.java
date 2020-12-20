package com.platform.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.platform.authentication.authorization.PlatformAuthenticationHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${resource.id:authenticationservice}")
	private String resourceId;
	
    @Autowired
	private PlatformAuthenticationHandler platformAuthenticationHandler;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
	    	.antMatchers("/error/**").permitAll()
	        .antMatchers("/oauth/**").permitAll()
	        .antMatchers("/managerLogin").permitAll()
	        .antMatchers("/memberList").permitAll()
	        .antMatchers("/api/member/manage/**").access("hasRole('ROLE_HEAD_ADMIN')")
	        .and()
	        .formLogin().loginPage("/managerLogin").failureUrl("/managerLogin?error").usernameParameter("username").passwordParameter("password")
	        .successHandler(platformAuthenticationHandler).failureHandler(platformAuthenticationHandler)
	        .and()
	        .logout().logoutSuccessUrl("/managerLogin?logout")
	        .and()
	        .exceptionHandling().accessDeniedPage("/403")
	        .and()
	        .csrf().disable();
		http.exceptionHandling().accessDeniedHandler(platformAuthenticationHandler);
		
	}
}