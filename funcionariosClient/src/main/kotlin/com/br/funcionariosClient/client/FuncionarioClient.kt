package com.br.funcionariosClient.client

import com.br.funcionariosClient.modelo.FuncionarioResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    value = "funcionarios",
    url = "http://localhost:8081/oauth2-resourceserver-gestao-funcionarios/api/funcionarios",
)
interface FuncionarioClient {

    @GetMapping
    fun getFuncionarios(): List<FuncionarioResponse>

}


