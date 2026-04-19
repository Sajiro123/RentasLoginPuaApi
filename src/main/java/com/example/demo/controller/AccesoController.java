package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.service.AccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acceso")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (Angular frontend)
public class AccesoController {

    @Autowired
    private AccesoService accesoService;

    /**
     * POST /api/acceso/login
     *
     * Body JSON esperado:
     * {
     *   "usuario": "JPEREZ",
     *   "clave": "1234",
     *   "codSistema": "REN"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = accesoService.validarAcceso(request);

        // Si el acceso es exitoso (valida='0') retornamos 200, en cualquier otro caso 401
        if ("0".equals(response.getValida())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
