package com.ajurasz.config;

import com.ajurasz.authentication.CompanyDetailsService;
import com.ajurasz.authentication.CompanyDetailsServiceImpl;
import com.ajurasz.authentication.SimpleAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author Arek Jurasz
 */
@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
@PropertySource("classpath:app.properties")
public class SecurityConfig {

    @Autowired
    Environment env;

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
        simpleAuthenticationSuccessHandler.setDefaultTargetUrl(env.getProperty("default-url"));
        simpleAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return simpleAuthenticationSuccessHandler;
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("SpiderKey", companyDetailsService());
    }
}
