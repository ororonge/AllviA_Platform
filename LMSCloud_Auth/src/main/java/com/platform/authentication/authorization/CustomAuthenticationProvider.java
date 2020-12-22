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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        // bcrypt�� ��й�ȣ üũ �޼ҵ�
		if (!encoder.matches(member_pwd, member.getPwd())) {
			throw new BadCredentialsException(member_username);
		}
 
		if (StringUtils.equals(member.getUserLoginNotAllowYn(), "Y")) {
			throw new DisabledException(member_username);
		}
        
//		String token = redisTokenUtil.authorization(member);
//		member.setJwtToken(token);
		
		// ������ ��ť��Ƽ ���� Ŭ������ ���� ��ū ����
//		CustomBearerTokenAuthenticationToken result = new CustomBearerTokenAuthenticationToken(token);
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(member, member_username, member.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(result);
		
		return result;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
