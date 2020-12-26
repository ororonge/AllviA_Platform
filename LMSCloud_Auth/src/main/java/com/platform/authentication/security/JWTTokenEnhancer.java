package com.platform.authentication.security;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.platform.authentication.mapper.ManageMemberMapper;
import com.platform.authentication.model.UserOrganizationDTO;

public class JWTTokenEnhancer implements TokenEnhancer {
    
	@Autowired
    private ManageMemberMapper manageMemberMapper;

    private String getUserOrganizationId(String userId){
    	UserOrganizationDTO orgUser = manageMemberMapper.selectUserOrganizationId(userId);
        return orgUser.getOrganizationId();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String orgId =  getUserOrganizationId(authentication.getName());

        additionalInfo.put("organizationId", orgId);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
