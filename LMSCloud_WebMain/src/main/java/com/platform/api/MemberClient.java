package com.platform.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="api-main", url="http://192.168.135.128:8480")
public interface MemberClient {
	@GetMapping("/api/member/memberList")
    String testFeign();
}
