package com.br.funcionariosClient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableFeignClients
class FuncionariosClientApplication

fun main(args: Array<String>) {
	runApplication<FuncionariosClientApplication>(*args)
}
