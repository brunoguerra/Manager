package com.ajurasz.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Arek Jurasz
 */
@Order(1)
public class WebAppSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
