package com.ajurasz.config;

import com.ajurasz.authentication.CompanyDetailsService;
import com.ajurasz.authentication.CompanyDetailsServiceImpl;
import com.ajurasz.authentication.SimpleAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Arek Jurasz
 */
@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
public class SecurityConfig {
    @Bean
    public CompanyDetailsService companyDetailsService() {
        return new CompanyDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleAuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleAuthenticationSuccessHandler simpleAuthenticationSuccessHandler =
                new SimpleAuthenticationSuccessHandler();
        simpleAuthenticationSuccessHandler.setDefaultTargetUrl("http://localhost:8080/index");
        simpleAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return simpleAuthenticationSuccessHandler;
    }
}
