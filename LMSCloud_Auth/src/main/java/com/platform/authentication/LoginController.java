package com.platform.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.model.SecurityComponent;

//@RequestMapping(value="auth")
@Controller
public class LoginController {
	
	@Autowired
	private SecurityComponent securityComponent;
	
	@GetMapping(value = { "/auth/managerLogin", "/managerLogin" })
    public String login() {
        return "managerLogin";
    }
	
//	@PostMapping("/login")
//    public Response login(@RequestBody ManagementLoginDTO user,
//                          HttpServletRequest req,
//                          HttpServletResponse res) {
//        try {
//            final UserDetails member = platformUserDetailsService.loadUserByUsername(user.getUsername());
//            final String token = jwtUtil.generateToken(member);
//            final String refreshJwt = jwtUtil.generateRefreshToken(member);
//            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
//            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
//            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
//            res.addCookie(accessToken);
//            res.addCookie(refreshToken);
//            return new Response(200, token);
//        } catch (Exception e) {
//            return new Response(500, e.getMessage());
//        }
//    }
	
	@GetMapping("/loginUser")
	@ResponseBody
    public ManagementLoginDTO loginUser(HttpServletRequest req, HttpServletResponse res) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		ManagementLoginDTO userDetails = (ManagementLoginDTO)principal; 
        return userDetails;
    }
	
	@RequestMapping(value="/error/web403Error", method=RequestMethod.GET)
    public String web403Error(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(securityComponent.getUserId());
        return "error/web403Error";
    }
	
	@RequestMapping(value="/common/NewFile", method=RequestMethod.GET)
    public String NewFile(HttpServletRequest req, HttpServletResponse res) {
        return "common/NewFile";
    }
}
