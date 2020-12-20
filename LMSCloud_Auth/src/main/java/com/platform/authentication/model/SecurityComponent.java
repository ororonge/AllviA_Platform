package com.platform.authentication.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityComponent {

	public String getUserIdx(){
		return getManagementLoginInfo().getUserIdx();
	}
	
	public String getUserId() {
		return getManagementLoginInfo().getUserId();
	}
	
	public String getUserNm() {
		return getManagementLoginInfo().getUserNm();
	}
	
	public String getAuthCd() {
		return getManagementLoginInfo().getAuthCd();
	}
	
	public ManagementLoginDTO getManagementLoginInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if(principal instanceof UserDetails) {
			ManagementLoginDTO user = (ManagementLoginDTO)principal;
			return user;
		}else {
			return new ManagementLoginDTO();
		}
	}
}
