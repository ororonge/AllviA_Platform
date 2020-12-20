package com.platform.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.authentication.authorization.ManagementUser;
import com.platform.authentication.token.CookieUtil;
import com.platform.authentication.token.JwtUtil;
import com.platform.authentication.token.RedisUtil;

import io.micrometer.core.ipc.http.HttpSender.Response;

@Controller
public class LoginController {
	
	@Autowired
	private UserDetailsService platformUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CookieUtil cookieUtil;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@GetMapping(value = { "/auth/managerLogin", "/managerLogin" })
    public ModelAndView login() {
		ModelAndView mav = new ModelAndView("auth/managerLogin");
        return mav;
    }
	
	@PostMapping("/login")
    public Response login(@RequestBody ManagementUser user,
                          HttpServletRequest req,
                          HttpServletResponse res) {
        try {
            final UserDetails member = platformUserDetailsService.loadUserByUsername(user.getUsername());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            return new Response(200, token);
        } catch (Exception e) {
            return new Response(500, e.getMessage());
        }
    }
}
