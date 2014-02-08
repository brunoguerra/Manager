package com.ajurasz.authentication;

import com.ajurasz.model.Company;
import com.ajurasz.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Arek Jurasz
 */
@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CompanyDetailsServiceImpl.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    private CompanyRepository companyRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Validate incoming user " + username);

        Company company = companyRepo.findCompanyByUsername(username);

        if(company == null) {
            log.info("No user with this " + username + " email");
            throw new UsernameNotFoundException(messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, null));
        }

        return company;
    }
}
