package com.platform.authentication.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/*resource config는 사용하지 않음*/
//@Configuration
//@EnableResourceServer
public class ResourceServerConfiguration implements ResourceServerConfigurer {

	@Value("${resource.id:authenticationservice}")
	private String resourceId;
	
//    // 모든 권한
//    private final String[] PUBLIC_RESOURCES = new String[]{
//    	
//    };
//    // 특정 권한
//    private final String[] PRIVATE_RESOURCES = new String[]{
//    };
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers(PUBLIC_RESOURCES).permitAll();
//		http.authorizeRequests().antMatchers(PRIVATE_RESOURCES).hasRole("HEAD_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.csrf().disable();
	}
}