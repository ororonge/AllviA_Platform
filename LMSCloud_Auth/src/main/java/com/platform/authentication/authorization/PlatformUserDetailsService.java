package com.platform.authentication.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.platform.authentication.mapper.ManageMemberMapper;

@Service
public class PlatformUserDetailsService implements UserDetailsService {

	@Autowired
    private ManageMemberMapper manageMemberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails result = manageMemberMapper.selectUserMstMng(username);	
		return result;
	}
}
