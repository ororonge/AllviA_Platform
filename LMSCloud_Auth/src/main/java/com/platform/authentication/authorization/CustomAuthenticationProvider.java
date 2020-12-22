package com.platform.authentication.authorization;

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
import com.platform.authentication.token.RedisTokenUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
    @Autowired
    private CustomPasswordEncoder encoder;
    
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
        
		String token = redisTokenUtil.authorization(member);
		member.setJwtToken(token);
		
		// 스프링 시큐리티 내부 클래스로 인증 토큰 생성
//		CustomBearerTokenAuthenticationToken result = new CustomBearerTokenAuthenticationToken(token);
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(member, token, member.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(result);
		
		return result;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
