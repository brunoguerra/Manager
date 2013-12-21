package com.ajurasz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Locale;

/**
 * @author Arek Jurasz
 */
@Configuration
public class InternationalizationConfig {

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale.Builder().setLanguage("pl").setRegion("PL").build());
        return sessionLocaleResolver;
    }

    @Bean
    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping(LocaleChangeInterceptor localeChangeInterceptor) {
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.setInterceptors(new Object[] {localeChangeInterceptor});
        return requestMappingHandlerMapping;
    }

    //Register properties with messsages
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource =
                new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:messages");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return reloadableResourceBundleMessageSource;
    }
}
