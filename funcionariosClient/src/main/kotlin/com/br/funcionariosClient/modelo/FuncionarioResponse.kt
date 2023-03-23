package com.br.funcionariosClient.modelo

import java.beans.ConstructorProperties


data class FuncionarioResponse @ConstructorProperties("id", "nome", "cpf", "cargo")

constructor(

    val id: Long,
    val nome: String,
    val cpf: String,
    val cargo: Cargo,

    ) {


//    constructor(funcionario: Funcionario) : this(id, nome, cpf, cargo) {
//        id = funcionario.id
//        nome = funcionario.nome
//        this.cpf = funcionario.cpf
//        this.cargo = funcionario.cargo

    //  }

//    val mapper = jacksonObjectMapper()
//    val state = mapper.readValue<FuncionarioResponse>(json)
//


//    // or
//    val state: MyStateObject = mapper.readValue(json)
//// or
//    myMemberWithType = mapper.readValue(json)


//        val mapper = jacksonObjectMapper()
//        val state = mapper.readValue<FuncionarioResponse>()
//
//        val jsonMapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
//
//        val retrofit = FuncionarioResponse.Builder()
//        ...
//        .addConverterFactory(JacksonConverterFactory.create(jsonMapper))
//            .build()
}