package com.example.funcionariosServer.funcionarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .logout().disable()
                .headers().frameOptions().deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/funcionarios").hasAuthority(Role.READ.value)
                .antMatchers(HttpMethod.GET, "/api/funcionarios/**").hasAuthority(Role.READ.value)
                .antMatchers(HttpMethod.POST, "/api/funcionarios").hasAuthority(Role.WRITE.value)
                .antMatchers(HttpMethod.DELETE, "/api/funcionarios").hasAuthority(Role.WRITE.value)
                .anyRequest().hasAnyAuthority(Role.WRITE.value)
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
