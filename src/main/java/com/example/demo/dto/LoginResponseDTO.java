package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    /**
     * Resultado de la validación del SP:
     * '0' => Acceso exitoso
     * '1' => Usuario no existe
     * '2' => Contraseña inválida
     * '3' => No tiene acceso al módulo
     * '4' => Usuario ya accedió
     * '5' => Fecha de caducidad
     */
    private String valida;
    private String mensaje;

    // Datos del usuario (sólo presentes cuando valida='0')
    private Integer codArea;
    private Integer nroCaja;
    private String nomUsuario;
    private Integer codUsuario;
}
