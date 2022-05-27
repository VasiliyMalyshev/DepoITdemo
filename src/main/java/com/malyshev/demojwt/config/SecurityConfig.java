package com.malyshev.demojwt.config;

import com.malyshev.demojwt.security.SuccessUserHandler;
import com.malyshev.demojwt.security.jwt.JwtConfigurer;
import com.malyshev.demojwt.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String ADMIN_ENDPOINT = "/admin/**";
    private final String LOGIN_ENDPOINT = "/login";
    private final JwtTokenProvider jwtTokenProvider;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, SuccessUserHandler successUserHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.successUserHandler = successUserHandler;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
