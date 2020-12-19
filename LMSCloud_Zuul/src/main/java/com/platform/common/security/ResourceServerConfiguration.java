package com.platform.common.security;

import javax.ws.rs.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
            .authorizeRequests()
            .antMatchers("/api/**")
            .hasRole("HEAD_ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .authorizeRequests()
            .antMatchers("/api/**")
            .hasRole("FRANCHISE_ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin().loginPage("/login").defaultSuccessUrl("/main", true);
    }
}
