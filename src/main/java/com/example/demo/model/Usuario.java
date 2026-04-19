package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuarios") // Asegúrate que así se llame en BdMunic
@Data // Esto genera Getters y Setters automáticamente con Lombok
public class Usuario {
    @Id
    private String login;
    private String password;
    private String nombre;
}