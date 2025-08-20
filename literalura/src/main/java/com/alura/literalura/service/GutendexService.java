package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Libro buscarLibro(String titulo) {
        try {
            String url = "https://gutendex.com/books?search=" + titulo.replace(" ", "%20");
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JsonNode root = new ObjectMapper().readTree(response.getBody());
            JsonNode results = root.path("results");

            if (results.isEmpty() || results.size() == 0) {
                return null; 
            }

            JsonNode libroJson = results.get(0);

            JsonNode autorJson = libroJson.path("authors").get(0);
            Autor autor = new Autor();
            autor.setNombre(autorJson.path("name").asText());
            autor.setNacimiento(autorJson.path("birth_year").isMissingNode() ? null : autorJson.path("birth_year").asInt());
            autor.setFallecimiento(autorJson.path("death_year").isMissingNode() ? null : autorJson.path("death_year").asInt());

            Libro libro = new Libro();
            libro.setTitulo(libroJson.path("title").asText());
            libro.setIdioma(libroJson.path("languages").get(0).asText());
            libro.setDescargas(libroJson.path("download_count").asInt());
            libro.setAutor(autor);

            return libro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
