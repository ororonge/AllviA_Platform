package com.platform.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.platform.web.service.CommonService;

@Controller
public class CommonController {

	@Autowired
    private CommonService commonService;
	
	@RequestMapping(value="/NewFile", method=RequestMethod.GET)
	public String NewFile() throws Exception {
		ModelAndView mav = new ModelAndView("NewFile");
		mav.addObject("name", "goddaehee");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query", "SELECT * FROM USER_MST_ST LIMIT 10");
		List<Map<String, Object>> list = commonService.getQuery(paramMap);
		return "NewFile";
	}
}
