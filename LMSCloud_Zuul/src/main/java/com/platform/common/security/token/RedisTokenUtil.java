package com.platform.common.security.token;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.common.security.model.ManagementLoginDTO;

@Component
public class RedisTokenUtil {

    @Autowired
    private JwtUtil jwtUtil;
 
	@Value("${spring.redis.duration}")
	private long duration;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public Boolean expire(String key) {
		return stringRedisTemplate.expire(key, duration, TimeUnit.SECONDS);
	}

	public String getData(String key) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	public void setData(String key, String value) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(key, value, duration, TimeUnit.SECONDS);
	}

	public void setDataExpire(String key, String value, long duration) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		Duration expireDuration = Duration.ofSeconds(duration);
		valueOperations.set(key, value, expireDuration);
	}

	public void deleteData(String key) {
		stringRedisTemplate.delete(key);
	}
    
    public String authorization(ManagementLoginDTO member) {
    	ObjectMapper mapper = new ObjectMapper();
    	String token = jwtUtil.generateToken(member);
		String loginJson = "";
		try {
			loginJson = mapper.writeValueAsString(member);
		} catch (JsonProcessingException e) {
		}
		deleteData(token);
		setData(token, loginJson);
		return token;
    }
    
    public ManagementLoginDTO getManagementLoginInfo(String token) {
    	ManagementLoginDTO memberData = new ManagementLoginDTO();
    	ObjectMapper mapper = new ObjectMapper();
    	String loginJson = getData(token);
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
