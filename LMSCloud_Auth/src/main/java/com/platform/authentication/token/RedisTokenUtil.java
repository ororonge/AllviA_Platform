package com.platform.authentication.token;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.authentication.model.ManagementLoginDTO;

@Component
public class RedisTokenUtil {

    @Autowired
    private JwtUtil jwtUtil;
 
    @Autowired
	private RedisUtil redisUtil;

	@Value("${spring.redis.duration}")
	private long duration;
    
    public String authorization(ManagementLoginDTO member) {
    	ObjectMapper mapper = new ObjectMapper();
    	String token = jwtUtil.generateToken(member);
		String loginJson = "";
		try {
			loginJson = mapper.writeValueAsString(member);
		} catch (JsonProcessingException e) {
		}
		redisUtil.deleteData(token);
		redisUtil.setData(token, loginJson);
		return token;
    }
    
    public ManagementLoginDTO getManagementLoginInfo(String token) {
    	ManagementLoginDTO memberData = new ManagementLoginDTO();
    	ObjectMapper mapper = new ObjectMapper();
    	String loginJson = redisUtil.getData(token);
    	if (StringUtils.isEmpty(loginJson)) {
    		return memberData;
		}
		try {
			memberData = mapper.readValue(loginJson, ManagementLoginDTO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return memberData;
    }
}
