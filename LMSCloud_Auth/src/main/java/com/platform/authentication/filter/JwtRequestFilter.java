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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.token.CookieUtil;
import com.platform.authentication.token.JwtUtil;
import com.platform.authentication.token.RedisTokenUtil;

public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
	
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;
    
    @Autowired
    private RedisTokenUtil redisTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    	String authToken = httpServletRequest.getHeader("Authorization");
    	if (StringUtils.startsWith(authToken, "Basic ") && StringUtils.isNotEmpty(authToken)) {
    		authToken = authToken.replace("Basic ", "");	
    	}
    	else {
    		authToken = "";
    	}
//    	if (StringUtils.isEmpty(authToken)) {
//    		LOGGER.error("JwtRequestFilterError authToken empty");
//    		httpServletResponse.sendRedirect("/login");
//    	}
//    	System.out.println(authToken);
//    	Object principal = httpServletRequest.getUserPrincipal();
//    	String jwtToken = "";
//    	if (principal == null) {
//    		LOGGER.error("JwtRequestFilterError principal empty");
//    		httpServletResponse.sendRedirect("/login");
//    	}
//    	if (!(principal instanceof UserDetails)) {
//    		LOGGER.error("JwtRequestFilterError UserDetails empty");
//    	}
//		ManagementLoginDTO member = (ManagementLoginDTO)principal;
//		if (StringUtils.isEmpty(member.getUserId())) {
//			LOGGER.error("JwtRequestFilterError UserDetails id empty");
//			httpServletResponse.sendRedirect("/login");
//		}
//		jwtToken = redisTokenUtil.authorization(member);
//		if (StringUtils.isEmpty(jwtToken)) {
//			LOGGER.error("JwtRequestFilterError jwtToken empty");
//			httpServletResponse.sendRedirect("/login");
//		}
//		member.setSecurityToken(authToken);
//		Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, jwtToken);
//		httpServletResponse.addCookie(accessToken);	
    }
}