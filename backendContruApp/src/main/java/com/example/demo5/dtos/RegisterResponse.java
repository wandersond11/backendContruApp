package com.example.demo5.dtos;

import com.example.demo5.domain.Cliente;

public class RegisterResponse {
    private final String jwttoken;
    private final Cliente cliente;

    public RegisterResponse(String jwttoken, Cliente cliente) {
        this.jwttoken = jwttoken;
        this.cliente = cliente;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
