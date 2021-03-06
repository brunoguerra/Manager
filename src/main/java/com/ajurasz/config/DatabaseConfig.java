package com.ajurasz.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ajurasz
 */
@PropertySource(value = "classpath:data-access.properties")
@EnableTransactionManagement
@EnableJpaRepositories("com.ajurasz.repository")
@Configuration
public class DatabaseConfig {

    @Autowired
    Environment environment;

    @Autowired
    MessageSource messageSource;

    @Bean
    public BoneCPDataSource dataSource() {
        BoneCPDataSource ds = new BoneCPDataSource();
        ds.setDriverClass(environment.getProperty("jdbc.driverClassName"));
        ds.setJdbcUrl(environment.getProperty("jdbc.url"));
        ds.setUsername(environment.getProperty("jdbc.username"));
        ds.setPassword(environment.getProperty("jdbc.password"));

        return ds;
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean validatorFactoryBean =
                new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(BoneCPDataSource dataSource, LocalValidatorFactoryBean validator) {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty("jpa.database")));
        jpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(environment.getProperty("jpa.generateDdl")));
        jpaVendorAdapter.setShowSql(Boolean.parseBoolean(environment.getProperty("jpa.showSql")));

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        jpaProperties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        jpaProperties.setProperty("hibernate.connection.CharSet", environment.getProperty("hibernate.connection.CharSet"));
        jpaProperties.setProperty("hibernate.connection.characterEncoding", environment.getProperty("hibernate.connection.characterEncoding"));
        jpaProperties.setProperty("hibernate.connection.useUnicode", environment.getProperty("hibernate.connection.useUnicode"));

        Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();
        jpaPropertyMap.put("javax.persistence.validation.factory", validator);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.ajurasz.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(jpaPropertyMap);
        em.setJpaProperties(jpaProperties);
        em.afterPropertiesSet();

        return em;
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
        return transactionManager;
    }
}
