package com.platform.authentication.authorization;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.token.JwtUtil;
import com.platform.authentication.token.RedisTokenUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
    @Autowired
    private CustomPasswordEncoder encoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String member_username = (String) authentication.getPrincipal();
		String member_pwd = (String) authentication.getCredentials();
		ManagementLoginDTO member = userDetailsService.loadUserByUsername(member_username);
        // bcrypt의 비밀번호 체크 메소드
		if (!encoder.matches(member_pwd, member.getPwd())) {
			throw new BadCredentialsException(member_username);
		}
 
		if (StringUtils.equals(member.getUserLoginNotAllowYn(), "Y")) {
			throw new DisabledException(member_username);
		}
        
		List<CustomGrantedAuthority> roles = member.getAuthorities();
		
		String token = redisTokenUtil.authorization(member);
		
		// 스프링 시큐리티 내부 클래스로 인증 토큰 생성
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(token, token, roles);
		
		SecurityContextHolder.getContext().setAuthentication(result);
		
		return result;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
