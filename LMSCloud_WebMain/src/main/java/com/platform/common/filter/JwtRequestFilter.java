package com.platform.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.ZuulServletFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.platform.common.security.model.ManagementLoginDTO;
import com.platform.common.security.token.CookieUtil;
import com.platform.common.security.token.JwtUtil;
import com.platform.common.security.token.RedisTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends ZuulServletFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CookieUtil cookieUtil;

	@Autowired
	private RedisTokenUtil redisTokenUtil;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
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
				RequestContext ctx = RequestContext.getCurrentContext();
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
		
		super.doFilter(servletRequest, servletResponse, filterChain);
	}
}
