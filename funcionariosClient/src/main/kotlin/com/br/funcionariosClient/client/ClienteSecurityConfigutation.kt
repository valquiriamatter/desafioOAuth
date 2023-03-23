package com.br.funcionariosClient.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository

@Configuration
class ClienteSecurityConfigutation {

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository?, // carrega as informações dos clients registrados no application.yml
        clientService: OAuth2AuthorizedClientService? //gerencia os tokens obtidos do Authorization Server

    ): OAuth2AuthorizedClientManager {

        //define quais estratégias serão usadas para autenticar/autorizar os clients
        val provider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            //.password()
            .build()

        //pode ser utilizado para obter os tokens manualmente se assim desejarmos, pois ele é quem de fato cuida de 90% deste workflow.
        val manager = AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService)
        manager.setAuthorizedClientProvider(provider)

        return manager
    }

    @Bean
    fun oAuth2FeignRequestInterceptor(clientManager: OAuth2AuthorizedClientManager?): OAuth2FeignRequestInterceptor? {
        return OAuth2FeignRequestInterceptor(clientManager, "funcionarios")
    }
}
