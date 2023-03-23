package com.example.funcionariosServer;

import base.SpringBootIntegrationTest;
import com.example.funcionariosServer.funcionarios.Cargo;
import com.example.funcionariosServer.funcionarios.Funcionario;
import com.example.funcionariosServer.funcionarios.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ListaFuncionariosControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private FuncionarioRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveListarTodosOsFuncionarios() throws Exception {
        // cenário
        List.of(
                new Funcionario("Rafael", "188.332.250-20", Cargo.TESTADOR, new BigDecimal("2.99")),
                new Funcionario("Alberto", "525.894.650-92", Cargo.GERENTE, new BigDecimal("1.99")),
                new Funcionario("Jordi", "994.300.560-26", Cargo.DESENVOLVEDOR, new BigDecimal("3.99"))
        ).forEach(funcionario -> {
            repository.save(funcionario);
        });

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nome").value("Alberto"))
                .andExpect(jsonPath("$[1].nome").value("Jordi"))
                .andExpect(jsonPath("$[2].nome").value("Rafael"))
        ;
    }

    @Test
    public void naoDeveListarTodosOsFuncionarios_quandoNaoHouverFuncionariosCadastrados() throws Exception {
        // cenário
        repository.deleteAll();

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
        ;
    }

    //401
    @Test
    public void naoDeveListarTodosOsFuncionariosSemToken() throws Exception {
        // cenário
        List.of(
                new Funcionario("Rafael", "188.332.250-20", Cargo.TESTADOR, new BigDecimal("2.99")),
                new Funcionario("Alberto", "525.894.650-92", Cargo.GERENTE, new BigDecimal("1.99")),
                new Funcionario("Jordi", "994.300.560-26", Cargo.DESENVOLVEDOR, new BigDecimal("3.99"))
        ).forEach(funcionario -> {
            repository.save(funcionario);
        });

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios"))
                .andExpect(status().isUnauthorized());
    }

    //403
    @Test
    public void naoDeveListarTodosOsFuncionariosSemPermissao() throws Exception {
        // cenário
        List.of(
                new Funcionario("Rafael", "188.332.250-20", Cargo.TESTADOR, new BigDecimal("2.99")),
                new Funcionario("Alberto", "525.894.650-92", Cargo.GERENTE, new BigDecimal("1.99")),
                new Funcionario("Jordi", "994.300.560-26", Cargo.DESENVOLVEDOR, new BigDecimal("3.99"))
        ).forEach(funcionario -> {
            repository.save(funcionario);
        });

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios")
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }

}