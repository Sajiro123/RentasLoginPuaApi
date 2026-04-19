package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AccesoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LoginResponseDTO validarAcceso(LoginRequestDTO request) {
        return jdbcTemplate.execute(
                (Connection con) -> {
                    // Llamada al stored procedure con los 3 parámetros de entrada
                    try (CallableStatement cs = con.prepareCall(
                            "{call dbo.Sp_Se_Acceso_Sistema(?, ?, ?)}")) {

                        cs.setString(1, request.getUsuario());
                        cs.setString(2, request.getClave());
                        cs.setString(3, request.getCodSistema());

                        boolean hasResultSet = cs.execute();

                        if (hasResultSet) {
                            try (ResultSet rs = cs.getResultSet()) {
                                if (rs.next()) {
                                    String valida    = rs.getString(1) != null ? rs.getString(1).trim() : "1";
                                    Integer codArea  = rs.getObject(2) != null ? rs.getInt(2) : null;
                                    Integer nroCaja  = rs.getObject(3) != null ? rs.getInt(3) : null;
                                    String nomUsuar  = rs.getString(4) != null ? rs.getString(4).trim() : null;
                                    Integer codUsua  = rs.getObject(5) != null ? rs.getInt(5) : null;

                                    return new LoginResponseDTO(
                                            valida,
                                            resolverMensaje(valida),
                                            codArea,
                                            nroCaja,
                                            nomUsuar,
                                            codUsua
                                    );
                                }
                            }
                        }

                        // Si no hay resultado (caso raro), devolver error genérico
                        return new LoginResponseDTO("1", resolverMensaje("1"), null, null, null, null);
                    }
                }
        );
    }

    /**
     * Traduce el código de validación del SP a un mensaje legible.
     */
    private String resolverMensaje(String valida) {
        return switch (valida.trim()) {
            case "0" -> "Acceso exitoso";
            case "1" -> "Usuario no existe";
            case "2" -> "Contraseña inválida";
            case "3" -> "No tiene acceso al módulo";
            case "4" -> "El usuario ya inició sesión";
            case "5" -> "Acceso fuera del período habilitado";
            default  -> "Error desconocido";
        };
    }
}
