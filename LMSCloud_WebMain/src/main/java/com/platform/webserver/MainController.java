package com.platform.webserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@GetMapping("/main")
    public ModelAndView main() {
		ModelAndView mav = new ModelAndView("main");
        return mav;
    }
	
	@GetMapping("/managerLogin")
    public ModelAndView managerLogin() {
		ModelAndView mav = new ModelAndView("managerLogin");
        return mav;
    }
	
	@GetMapping("/member/test")
    public ModelAndView test() {
		ModelAndView mav = new ModelAndView("member/test");
        return mav;
    }
	
	@GetMapping("/error/web403Error")
    public ModelAndView web403Error(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("error/web403Error");
        return mav;
    }
}