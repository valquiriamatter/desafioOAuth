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
                .antMatchers(HttpMethod.GET, "/api/funcionarios").hasAuthority("SCOPE_funcionarios:read")
                .antMatchers(HttpMethod.GET, "/api/funcionarios/**").hasAuthority("SCOPE_funcionarios:read")
                .antMatchers(HttpMethod.POST, "/api/funcionarios").hasAuthority("SCOPE_funcionarios:write")
                .antMatchers(HttpMethod.DELETE, "/api/funcionarios").hasAuthority("SCOPE_funcionarios:write")
                .anyRequest().hasAnyAuthority("SCOPE_funcionarios:write")
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
