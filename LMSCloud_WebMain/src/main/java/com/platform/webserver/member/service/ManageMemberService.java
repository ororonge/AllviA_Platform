package com.platform.webserver.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.apiclient.ManageMemberClient;
import com.platform.apiclient.member.model.ManageMember;

@Service
public class ManageMemberService {

	@Autowired
	private ManageMemberClient manageMemberClient;
	
	public List<ManageMember> memberList() {
		List<ManageMember> memberList = manageMemberClient.memberList();
        return memberList;
    }
}
