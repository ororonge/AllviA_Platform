package com.platform.authentication.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.platform.authentication.authorization.CustomAuthenticationProvider;
import com.platform.authentication.authorization.CustomUserDetailsService;
import com.platform.authentication.authorization.Http401AuthenticationEntryPoint;
import com.platform.authentication.filter.CustomUsernamePasswordAuthenticationFilter;
import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.token.CookieUtil;
import com.platform.authentication.token.JwtUtil;
import com.platform.authentication.token.RedisTokenUtil;

@EnableWebSecurity
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private CookieUtil cookieUtil;
    
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    
    @Override()
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    // 권한 필터링과 중복되어도 무시함
    private final String[] ANY_RESOURCES = new String[]{
		"/auth/oauth/token", "/login", "/userlogin", "/resources/**", "/images/**", "/css/**", "/js/**"
    };

    @Override
	public void configure(WebSecurity web) throws Exception
	{
    	web.ignoring().antMatchers(ANY_RESOURCES);
	}
    
    /**
     * OAuth2 방식
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService);
    }
    
    /**
     * Security 방식
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
	        .addFilterAfter(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessHandler(this::logoutSuccessHandler)
	        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(new Http401AuthenticationEntryPoint("401"));
    }
    
    @Bean
    public CustomUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
    	CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/userlogin", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }
    
    private void loginSuccessHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
    	Object principal = authentication.getPrincipal();
    	String token = "";
    	if (principal != null && principal instanceof UserDetails) {
    		ManagementLoginDTO member = (ManagementLoginDTO)principal;
    		token = redisTokenUtil.authorization(member);
    	}
    	if (StringUtils.isNotEmpty(token)) {
    		Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
        	response.addCookie(accessToken);	
    	}
    	SecurityContextHolder.getContext().setAuthentication(authentication);
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("/authmain");
    }
 
    private void loginFailureHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException e) throws IOException {
 
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
 
    private void logoutSuccessHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
 
        response.setStatus(HttpStatus.OK.value());
    }
}
