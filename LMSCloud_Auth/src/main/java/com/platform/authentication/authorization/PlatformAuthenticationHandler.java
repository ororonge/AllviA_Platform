package com.platform.authentication.authorization;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PlatformAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, AccessDeniedHandler {
	private static final Logger log = LoggerFactory.getLogger(PlatformAuthenticationHandler.class);

    @Value("${spring.security.error.403.url}")
    private String web403ErrorUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		ObjectMapper om = new ObjectMapper();

		String redirectUrl = getReturnUrl(request, response);
		log.debug("RedirectUrl : {}", redirectUrl);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("returnUrl", redirectUrl);
		
		Object details = authentication.getPrincipal();
		if(details instanceof String) {
			map.put("name", (String) details);
			map.put("username", (String) details);
		}

		log.debug("User : {}", map);
		
		// {"success" : true, "returnUrl" : "..."}
		String jsonString = om.writeValueAsString(map);

		OutputStream out = response.getOutputStream();
		out.write(jsonString.getBytes());
	}

	/**
	 * 로그인 하기 전의 요청했던 URL을 알아낸다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			return request.getSession().getServletContext().getContextPath();
		}
		return savedRequest.getRedirectUrl();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
		throws IOException, ServletException {

		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("message", exception.getMessage());

		// {"success" : false, "message" : "..."}
		String jsonString = om.writeValueAsString(map);

		OutputStream out = response.getOutputStream();
		out.write(jsonString.getBytes());
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect(web403ErrorUrl);
	}
}