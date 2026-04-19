package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String usuario;
    private String clave;
    private String codSistema;
}
