package com.platform.authentication.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.platform.authentication.mapper.ManageMemberMapper;
import com.platform.authentication.model.ManagementLoginDTO;

@Service
public class PlatformUserDetailsService implements UserDetailsService {

	@Autowired
    private ManageMemberMapper manageMemberMapper;
	
	@Override
	public ManagementLoginDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		ManagementLoginDTO result = manageMemberMapper.selectUserMstMng(username);
		if (result != null) {
			result.setAuthorities(manageMemberMapper.selectUserMstMngAuthority(username));
		}
		return result;
	}
}
