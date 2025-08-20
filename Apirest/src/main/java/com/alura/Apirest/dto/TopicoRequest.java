package com.alura.Apirest.dto;

import jakarta.validation.constraints.NotBlank;

public class TopicoRequest {
    @NotBlank private String titulo;
    @NotBlank private String mensaje;
    @NotBlank private String curso;

    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getCurso() { return curso; }
}
