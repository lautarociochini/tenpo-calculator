package org.tenpo.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.tenpo.challenge.service.session.MyUserDetailsService;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService userDetailsServiceImpl;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private CompositeSessionAuthenticationStrategy concurrentSession;

    private static final String[] USER_ENDPOINTS = {
            "/v1/user/signup",
            "/v1/user/login",
            "/v1/user/logout"
    };

    private static final String[] CALCULATOR_ENDPOINTS = {
            "/v1/calculator/add"
    };

    private static final String[] OPERATION_ENDPOINTS = {
            "/v1/operation/findAll"
    };

    private static final String[] DOCS_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(USER_ENDPOINTS).permitAll()
                .antMatchers(CALCULATOR_ENDPOINTS).permitAll()
                .antMatchers(OPERATION_ENDPOINTS).permitAll()
                .antMatchers(DOCS_WHITELIST).permitAll()
                .anyRequest().denyAll()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement(session -> session
                        .sessionAuthenticationStrategy(concurrentSession)
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(-1)
                        .sessionRegistry(sessionRegistry)
                );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}