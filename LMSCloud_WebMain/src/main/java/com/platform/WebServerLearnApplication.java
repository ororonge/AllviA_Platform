package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WebServerLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServerLearnApplication.class, args);
	}
}
