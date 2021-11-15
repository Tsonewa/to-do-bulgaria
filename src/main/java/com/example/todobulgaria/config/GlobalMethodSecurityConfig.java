package com.example.todobulgaria.config;

import com.example.todobulgaria.services.TripEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private MethodSecurityExpresionHandler methodSecurityExpresionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return methodSecurityExpresionHandler;
    }

    @Bean
    public MethodSecurityExpresionHandler createExpressionHandler(TripEntityService tripEntityService) {
        return new MethodSecurityExpresionHandler(tripEntityService);
    }
}
