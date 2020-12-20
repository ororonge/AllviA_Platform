package com.platform.api.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.api.member.model.ManageMember;

@RestController
public class ManageMemberController {
	@GetMapping("/memberList")
	public List<ManageMember> memberList(@RequestParam(value = "userId", defaultValue = "", required=false) String userId) {
		List<ManageMember> resultList = new ArrayList<ManageMember>();
		for (int i = 0; i < 5; i++) {
			ManageMember member = new ManageMember();
			member.setUserIdx(i);
			member.setUserId("id : " + i);
			member.setUserNm("nm : " + i);
			resultList.add(member);
		}
		return resultList;
	}
	
	@GetMapping("/api/member/manage/memberList")
	public List<ManageMember> test(@RequestParam(value = "userId", defaultValue = "", required=false) String userId) {
		List<ManageMember> resultList = new ArrayList<ManageMember>();
		for (int i = 0; i < 5; i++) {
			ManageMember member = new ManageMember();
			member.setUserIdx(i);
			member.setUserId("test id : " + i);
			member.setUserNm("test nm : " + i);
			resultList.add(member);
		}
		return resultList;
	}
}