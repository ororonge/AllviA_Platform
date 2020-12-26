package com.platform.authentication.security;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.authentication.authorization.CustomPasswordEncoder;
import com.platform.authentication.authorization.CustomUserDetailsService;
import com.platform.authentication.model.ManagementLoginDTO;

public class JWTTokenEnhancer implements TokenEnhancer {
    
	@Autowired
	private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomPasswordEncoder encoder;
	
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
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
		ObjectMapper objectMapper = new ObjectMapper();
        if (member != null && StringUtils.isNotEmpty(member.getUserId())) {
        	@SuppressWarnings("unchecked")
			Map<String, Object> additionalInfo = objectMapper.convertValue(member, Map.class);
        	((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }
}
