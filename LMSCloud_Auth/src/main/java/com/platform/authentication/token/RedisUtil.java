package com.platform.authentication.token;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Value("${spring.redis.duration}")
	private long duration;

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
}