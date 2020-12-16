package com.platform.webserver.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.apiclient.member.model.ManageMember;
import com.platform.webserver.member.service.ManageMemberService;

@RestController
public class ManageMemberController {

	@Autowired
    private  ManageMemberService  manageMemberService;
	
	@GetMapping("/memberList")
    public List<ManageMember> testFeign() {
        return manageMemberService.memberList();
    }
}
