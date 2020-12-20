package com.platform.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration implements ResourceServerConfigurer {

	@Value("${aplication.name:web-main}")
	private String resourceId;
	
    @Autowired
	private PlatformAuthenticationHandler platformAuthenticationHandler;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/main").permitAll()
//	    	.antMatchers("/error/**").permitAll()
//	        .antMatchers("/main").permitAll()
//	        .antMatchers("/managerLogin").permitAll()
//	        .antMatchers("/memberList").permitAll()
//	        .antMatchers("/api/member/manage/**").access("hasRole('ROLE_HEAD_ADMIN')")
//	        .and()
//	        .formLogin().loginPage("/managerLogin").failureUrl("/managerLogin?error").usernameParameter("username").passwordParameter("password")
//	        .successHandler(platformAuthenticationHandler).failureHandler(platformAuthenticationHandler)
//	        .and()
//	        .logout().logoutSuccessUrl("/managerLogin?logout")
//	        .and()
//	        .exceptionHandling().accessDeniedPage("/403")
//	        .and()
//	        .csrf().disable();
//		http.exceptionHandling().accessDeniedHandler(platformAuthenticationHandler);
		http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
	        @Override
	        public void commence(HttpServletRequest request, HttpServletResponse response,
	                AuthenticationException authException) throws IOException, ServletException {
	            if (authException != null) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().print("Unauthorizated....");
	            }
	        }
	    });
		http.authorizeRequests().antMatchers("/**").permitAll().and().csrf().disable();
	}
}