package com.platform.webserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@GetMapping("/main")
    public ModelAndView testFeign() {
		ModelAndView mav = new ModelAndView();
        return mav;
    }
}