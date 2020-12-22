package com.platform.authentication.authorization;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;

public class CustomUserApprovalHandler implements UserApprovalHandler {

	@Override
	public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorizationRequest updateAfterApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getUserApprovalRequest(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication) {
		// TODO Auto-generated method stub
		return null;
	}

}
