package com.platform.authentication.token;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.authentication.model.ManagementLoginDTO;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CookieUtil cookieUtil;

	@Autowired
	private RedisTokenUtil redisTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException {

		final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest, JwtUtil.ACCESS_TOKEN_NAME);
		String jwt = null;
		ManagementLoginDTO member = new ManagementLoginDTO();
		try {
			if (jwtToken != null) {
				jwt = jwtToken.getValue();
				member = redisTokenUtil.getManagementLoginInfo(jwt);
			}
			if (StringUtils.isNotEmpty(member.getUserId()) && jwtUtil.validateToken(jwt, member)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		} catch (ExpiredJwtException e) {
		} catch (Exception e) {
		}
		try {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}