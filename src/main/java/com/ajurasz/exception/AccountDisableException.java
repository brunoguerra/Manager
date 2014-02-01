package com.ajurasz.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Arek Jurasz
 */
public class AccountDisableException extends AuthenticationException {

    public AccountDisableException(String msg) {
        super(msg);
    }
}
