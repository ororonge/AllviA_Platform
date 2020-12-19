package com.platform.webserver.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.platform.webserver.common.service.CommonService;

@Controller
public class CommonController {

	@Autowired
    private CommonService commonService;
}
