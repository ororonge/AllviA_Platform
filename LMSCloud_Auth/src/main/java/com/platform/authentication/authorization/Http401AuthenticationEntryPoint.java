package com.platform.authentication.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * AuthenticationEntryPoint that sends a 401 and Parameterized by the value of the
 * {@code WWW-Authenticate} header. Like the {@link BasicAuthenticationEntryPoint} but
 * more flexible.
 *
 * @author Dave Syer
 * @since 1.3.0
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final String headerValue;

	public Http401AuthenticationEntryPoint(String headerValue) {
		this.headerValue = headerValue;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("WWW-Authenticate", this.headerValue);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());
	}

}