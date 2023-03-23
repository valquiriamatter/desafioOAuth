package com.br.funcionariosClient.controller

import com.br.funcionariosClient.client.FuncionarioClient
import com.br.funcionariosClient.modelo.FuncionarioResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/funcionarios")
class FuncionarioController(
    private val client: FuncionarioClient
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun listarFuncionario(): ResponseEntity<*>? {

        System.out.println("Controller")
        var lista: List<FuncionarioResponse> = client.getFuncionarios()

        return ResponseEntity.ok(lista);
    }
}