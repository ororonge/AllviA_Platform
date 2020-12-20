package com.platform.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.platform.authentication.authorization.PlatformAuthenticationProvider;
import com.platform.authentication.authorization.PlatformUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PlatformAuthenticationProvider authenticationProvider;
	
    @Autowired
    private PlatformUserDetailsService userDetailsService;
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    @Qualifier("platformPasswordEncoder")
    private PasswordEncoder encoder;

    @Override
	public void configure(WebSecurity web) throws Exception
	{
		// 메인페이지 : css나 js 같은것들도 여기에 포함시켜준다.
		 web.ignoring().antMatchers("/resources/**", "/images/**", "/css/**", "/js/**");
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService);
    }
}
