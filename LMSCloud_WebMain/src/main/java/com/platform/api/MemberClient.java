package com.platform.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="LMSCloud_API", url="http://localhost:8401")
public interface MemberClient {
	@GetMapping("/api/member/memberList")
    String testFeign();
}
