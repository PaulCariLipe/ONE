package com.alura.Apirest.dto;

public class TokenDTO {
    private String token;
    private String type = "Bearer";

    public TokenDTO(String token) { this.token = token; }
    public String getToken() { return token; }
    public String getType() { return type; }
}
