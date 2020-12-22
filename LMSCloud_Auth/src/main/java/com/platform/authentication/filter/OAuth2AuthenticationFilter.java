package com.platform.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.token.CookieUtil;
import com.platform.authentication.token.JwtUtil;

public class OAuth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthenticationFilter.class);
	
    @Autowired
    private CookieUtil cookieUtil;
	
	public OAuth2AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//    	Enumeration params = httpServletRequest.getHeaderNames();
//    	while(params.hasMoreElements()) {
//    	  String name = (String) params.nextElement();
//    	  System.out.print(name + " : " + httpServletRequest.getHeader(name) + "     "); 
//    	}
//    	System.out.println(cookieUtil.getCookie(httpServletRequest, JwtUtil.ACCESS_TOKEN_NAME));;
    	String token = httpServletRequest.getHeader("Authorization");
        if (token.startsWith("Bearer")) {
        	token = StringUtils.removeStart(token, "Bearer").trim();	
        }
        else {
        	token = StringUtils.removeStart(token, "Basic").trim();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Object principal = auth.getPrincipal();
    	// 기존 member 정보를 기반으로 쿠키 키를 추출한다
    	if (principal != null && principal instanceof UserDetails) {
    		ManagementLoginDTO member = (ManagementLoginDTO)principal;
    		if (StringUtils.isEmpty(member.getUserId())) {
    			LOGGER.error("JwtRequestFilterError UserDetails id empty");
    			httpServletResponse.sendRedirect("/login");
    		}
    		member.setSecurityToken(token);
    		Cookie cookie = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, member.getJwtToken());
    		httpServletResponse.addCookie(cookie);
    	}
        // 이후 session에 담긴 member 정보는 날리고 redis로만 SSO 처리한다
        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
        return requestAuthentication;
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}