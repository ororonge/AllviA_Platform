package com.platform.apiclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.platform.apiclient.member.model.ManageMember;

@FeignClient(name="api-main", url="http://localhost:8401")
public interface ManageMemberClient {
	@GetMapping("/api/member/manage/memberList")
	List<ManageMember> memberList();
}