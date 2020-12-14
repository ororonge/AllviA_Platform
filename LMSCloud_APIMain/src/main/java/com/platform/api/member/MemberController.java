package com.platform.api.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {
	@GetMapping("/memberList")
	public Map<String, Object> memberList(@RequestParam(value = "userId", defaultValue = "", required=false) String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userId", userId);
		return resultMap;
	}
}