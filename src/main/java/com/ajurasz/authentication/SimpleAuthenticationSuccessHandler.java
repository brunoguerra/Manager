package com.ajurasz.authentication;

import com.ajurasz.model.Company;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Arek Jurasz
 */
public class SimpleAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 60 * 2);
        Company c = (Company) authentication.getPrincipal();
        session.setAttribute("company", c);

        //if user login for a first time redirect him to setting page
    }
}

