package com.alura.Apirest.controller;

import com.alura.Apirest.dto.LoginDTO;
import com.alura.Apirest.dto.TokenDTO;
import com.alura.Apirest.model.Usuario;
import com.alura.Apirest.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO login) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );
        Usuario usuario = (Usuario) auth.getPrincipal();
        String token = tokenService.generarToken(usuario.getUsername());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
