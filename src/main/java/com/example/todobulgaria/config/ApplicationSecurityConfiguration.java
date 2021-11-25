package com.example.todobulgaria.config;

import com.example.todobulgaria.security.UserDetailsImpl;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import javax.websocket.Endpoint;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetails;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(UserDetailsService userDetails, PasswordEncoder passwordEncoder) {
        this.userDetails = userDetails;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
http.
        authorizeRequests().
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                        antMatchers("/", "/users/login", "/users/register", "/about-us", "/trips/best", "/actuator/**", "/admin").permitAll().
                        antMatchers("/**").authenticated().
                and().
                        formLogin().
                        loginPage("/users/login").
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                        defaultSuccessUrl("/home").
                        failureForwardUrl("/users/login-error").
                and().
                logout().
                        logoutUrl("/users/logout").
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                        deleteCookies("JSESSIONID");


//        .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET", "PUT", "PATCH"))
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "false"))
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"));
    }
}
