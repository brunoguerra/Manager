package com.ajurasz.config;

import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.List;

/**
 * @author ajurasz
 */
@EnableWebMvc
@ComponentScan(basePackages = {"com.ajurasz.controller", "com.ajurasz.service", "com.ajurasz.model"})
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(TilesView.class);
        return urlBasedViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles.xml"});
        return tilesConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/**");
        registry.addResourceHandler("/documents/**").addResourceLocations("/WEB-INF/pdfs/documents/**");
        registry.addResourceHandler("/reports/**").addResourceLocations("/WEB-INF/pdfs/reports/**");
        registry.addResourceHandler("/invoices/**").addResourceLocations("/WEB-INF/pdfs/invoices/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver =
                new PageableHandlerMethodArgumentResolver();
        pageableHandlerMethodArgumentResolver.setFallbackPageable(new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "id")));

        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
    }
}
