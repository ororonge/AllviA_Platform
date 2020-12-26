package com.platform.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.platform.authentication.authorization.CustomAuthenticationProvider;
import com.platform.authentication.authorization.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Override()
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/login");
	}
    
//    /**
//     * OAuth2 ¹æ½Ä
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService);
//    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers("/spiters/me").authenticated()
////                .antMatchers(HttpMethod.POST,".spittles").authenticated()
////                .anyRequest().permitAll();
//        http
//        .authorizeRequests()
//            .antMatchers("/auth/login").permitAll();
//        }
}
