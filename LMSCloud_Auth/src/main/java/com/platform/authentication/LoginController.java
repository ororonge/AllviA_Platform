package com.platform.authentication;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.authentication.filter.OAuth2AuthenticationFilter;
import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.util.JwtUtil;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    private JwtUtil jwtUtil;
	
//	@Resource(name="customRedisTokenStore")
//	private TokenStore tokenStore;
//    @Bean(name="manageUserInfo")
//    public ManagementLoginDTO manageUserInfo() {
//    	ManagementLoginDTO userData = new ManagementLoginDTO();
//    	Collection<OAuth2AccessToken> tokenList = tokenStore.findTokensByClientIdAndUserName("head-admin", "testadmin");
//    	if (CollectionUtils.isEmpty(tokenList)) {
//    		return userData;
//    	}
//    	ObjectMapper objectMapper = new ObjectMapper();
//    	for (OAuth2AccessToken token : tokenList) {
//    		if (MapUtils.isEmpty(token.getAdditionalInformation())) {
//    			continue;
//    		}
//    		try {
//    			userData = objectMapper.convertValue(token.getAdditionalInformation(), ManagementLoginDTO.class);	
//			} catch (Exception e) {
//			}
//    	}
//    	return StringUtils.isEmpty(userData.getUserId()) ? new ManagementLoginDTO() : userData;
//    }
	
//	@Secured("ROLE_TEST")
	@GetMapping("/authmain")
    public ModelAndView main(HttpServletRequest req, Authentication authentication) {
		ModelAndView mav = new ModelAndView("authmain");
		String token = req.getHeader("Authorization");
		token = StringUtils.trim(StringUtils.replace(token, "Bearer", ""));
		String userId = jwtUtil.getUserId(token);
//		ManagementLoginDTO userData = manageUserInfo();
		ManagementLoginDTO userData = (ManagementLoginDTO) authentication.getPrincipal();;
        return mav;
    }
	
	@GetMapping("/login")
    public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
        return mav;
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping("/jwt/create_token")
	@ResponseBody
    public Map<String, Object> jwtToken(@RequestParam Map<String, Object> requestMap, HttpServletRequest request, HttpServletResponse response2) {
		String uri = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("grant_type", "password"));
		formparams.add(new BasicNameValuePair("scope", "webclient"));
		formparams.add(new BasicNameValuePair("username", "testadmin"));
		formparams.add(new BasicNameValuePair("password", "q12345"));
		String encoding = Base64.getEncoder().encodeToString(("head-admin" + ":" + "allvia-seckey-v0.0.1-head-admin").getBytes());
		HttpClient client = HttpClientBuilder.create().build(); // HttpClient 积己
		HttpPost postRequest = new HttpPost(uri + "/oauth/token"); //POST 皋家靛 URL 货己 
		postRequest.setHeader("Accept", "application/json");
		postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postRequest.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			HttpResponse response = client.execute(postRequest);
			//Response 免仿
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				ObjectMapper mapper = new ObjectMapper();
				resultMap = mapper.readValue(body, Map.class);
				LOGGER.info(body);
			} else {
				LOGGER.error("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			LOGGER.error(e.toString());
		}
		if (MapUtils.isEmpty(resultMap)) {
			resultMap.put("RESULT_CODE", "401");
			resultMap.put("RESULT_MESSAGE", "ERROR");
			return resultMap;
		}
		return resultMap;
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
