package com.br.funcionariosClient.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager


/**
 * Infelizmente a lib Open Feign ainda não suporta a versão do OAuth2 no Spring Security 5. Seu interceptor
 * padrão funciona somente com a versão antiga.
 *
 * Inpirado na implementação do @loesak
 * - https://github.com/loesak/spring-security-openfeign/blob/master/README.md
 * - https://www.baeldung.com/spring-cloud-feign-oauth-token
 */

class OAuth2FeignRequestInterceptor(

    private val authorizedClientManager: OAuth2AuthorizedClientManager?,
    private val clientRegistrationId: String

) : RequestInterceptor {

    override fun apply(request: RequestTemplate) {

        if (authorizedClientManager == null) {
            return
        }

        val authorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId(clientRegistrationId)
            .principal(ANONYMOUS_AUTHENTICATION)
            .build()

        val authorizedClient = authorizedClientManager.authorize(authorizeRequest)
            ?: throw IllegalStateException(
                "This client uses an authorization grant type that's not supported by the " +
                        "configured provider. ClientRegistrationId = " + clientRegistrationId
            )

        val accessToken = authorizedClient.accessToken
        request.header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken.tokenValue))
    }

    companion object {
        private val ANONYMOUS_AUTHENTICATION: Authentication = AnonymousAuthenticationToken(
            "anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
        )
    }
}