package com.platform.authclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="Simple-Gateway")
public interface AuthClient {

	@RequestMapping(method = RequestMethod.GET, value = "/gateway/test")
    String getSessionId(@RequestHeader("X-Auth-Token") String token);
}
