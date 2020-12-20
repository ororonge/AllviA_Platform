package com.platform.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//@RequestMapping(value="auth")
@Controller
public class LoginController {
	
//	@Autowired
//	private SecurityComponent securityComponent;
	
	@GetMapping("/authmain")
    public ModelAndView main() {
		ModelAndView mav = new ModelAndView("authmain");
        return mav;
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
	
//	@GetMapping("/loginUser")
//	@ResponseBody
//    public ManagementLoginDTO loginUser(HttpServletRequest req, HttpServletResponse res) {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
//		ManagementLoginDTO userDetails = (ManagementLoginDTO)principal; 
//        return userDetails;
//    }
	
//	@RequestMapping(value="/common/NewFile", method=RequestMethod.GET)
//    public String NewFile(HttpServletRequest req, HttpServletResponse res) {
//        return "common/NewFile";
//    }
}
