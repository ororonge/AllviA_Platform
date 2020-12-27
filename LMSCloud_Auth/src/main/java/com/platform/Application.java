package com.platform;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.authentication.security.OAuth2ServerProperties;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableResourceServer
@EnableAuthorizationServer
@EnableConfigurationProperties(OAuth2ServerProperties.class)
//java -jar -DPORT=8761 -Dspring.profiles.active=real -XX:MaxPermSize=128m -Xms256m -Xmx512m "C:/Users/samsung/git/repository/LMSCloud_Eureka/build/libs/LMSCloud_Eureka_0.0.1-SNAPSHOT.jar"
//curl -u head-admin:allvia-seckey-v0.0.1-head-admin http://localhost:8480/auth/oauth/token -d "grant_type=password&scope=webclient&username=testadmin&password=q12345"
public class Application {
    @RequestMapping(value = { "/user" }, produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}