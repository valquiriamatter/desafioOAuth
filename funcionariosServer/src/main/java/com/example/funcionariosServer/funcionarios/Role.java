package com.example.funcionariosServer.funcionarios;

public enum Role {

    READ ("SCOPE_funcionarios:read"),
    WRITE("SCOPE_funcionarios:write");

    public String value;

    Role(String role) {
        value = role;
    }

//    public enum CartasEnum {
//        J(11),Q(12),K(13),A(14);
//
//        public int valorCarta;
//        CartasEnum(int valor) {
//            valorCarta = valor;
//        }
//    }


}
