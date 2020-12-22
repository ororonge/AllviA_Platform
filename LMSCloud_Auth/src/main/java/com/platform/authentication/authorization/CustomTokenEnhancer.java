package com.platform.authentication.authorization;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.authentication.model.ManagementLoginDTO;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

    	if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserDetails) {
    		ManagementLoginDTO member = (ManagementLoginDTO) authentication.getPrincipal();
    		ObjectMapper objectMapper = new ObjectMapper();
            if (member != null && StringUtils.isNotEmpty(member.getUserId())) {
            	Map<String, Object> additionalInfo = objectMapper.convertValue(member, Map.class);
            	((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            }
    	}

        return accessToken;
    }

}