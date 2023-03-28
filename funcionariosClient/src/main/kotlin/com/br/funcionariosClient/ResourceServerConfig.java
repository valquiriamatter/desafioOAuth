package com.br.funcionariosClient;

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
                .antMatchers(HttpMethod.GET, "/funcionarios").hasAuthority("SCOPE_contatos:write")
                //.anyRequest().hasAnyAuthority("SCOPE_funcionarios:write")
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

//    @Bean
//    fun authorizedClientManager(
//            clientRegistration: ClientRegistrationRepository?,
//            authorizedClient: OAuth2AuthorizedClientRepository?
//    ): OAuth2AuthorizedClientManager? {
//        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
//                .builder()
//                .clientCredentials()
//                .build()
//        val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(clientRegistration, authorizedClient)
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
//        return authorizedClientManager
//    }
}
