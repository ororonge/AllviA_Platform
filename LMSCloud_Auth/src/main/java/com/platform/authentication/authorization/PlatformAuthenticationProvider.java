package com.platform.authentication.authorization;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.platform.authentication.model.ManagementLoginDTO;

@Component
public class PlatformAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private PlatformUserDetailsService userDetailsService;
	
    @Autowired
    @Qualifier("platformPasswordEncoder")
    private PasswordEncoder encoder;
 
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
        
		List<PlatformGrantedAuthority> roles = member.getAuthorities();
		// 스프링 시큐리티 내부 클래스로 인증 토큰 생성
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(member_username, member_pwd, roles);
 
		return result;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
