package com.platform.authentication;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.authentication.model.ManagementLoginDTO;

@Controller
public class LoginController {
	
    @Bean(name="manageUserInfo")
    public ManagementLoginDTO manageUserInfo() {
    	ManagementLoginDTO userData = new ManagementLoginDTO();
    	Collection<OAuth2AccessToken> tokenList = tokenStore.findTokensByClientIdAndUserName("head-admin", "testadmin");
    	if (CollectionUtils.isEmpty(tokenList)) {
    		return userData;
    	}
    	ObjectMapper objectMapper = new ObjectMapper();
    	for (OAuth2AccessToken token : tokenList) {
    		if (MapUtils.isEmpty(token.getAdditionalInformation())) {
    			continue;
    		}
    		try {
    			userData = objectMapper.convertValue(token.getAdditionalInformation(), ManagementLoginDTO.class);	
			} catch (Exception e) {
			}
    	}
    	return StringUtils.isEmpty(userData.getUserId()) ? new ManagementLoginDTO() : userData;
    }
	
	@Resource(name="customRedisTokenStore")
	private TokenStore tokenStore;
//	@Secured("ROLE_TEST")
	@GetMapping("/authmain")
    public ModelAndView main() {
		ModelAndView mav = new ModelAndView("authmain");
		ManagementLoginDTO userData = manageUserInfo();
        return mav;
    }
	
	@GetMapping("/login")
    public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
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
