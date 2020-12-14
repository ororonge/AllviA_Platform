package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LmsCloudApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsCloudApiApplication.class, args);
	}
}
