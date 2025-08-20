package com.alura.Apirest.controller;

import com.alura.Apirest.dto.TopicoRequest;
import com.alura.Apirest.model.Topico;
import com.alura.Apirest.model.Usuario;
import com.alura.Apirest.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepo;

    public TopicoController(TopicoRepository topicoRepo) {
        this.topicoRepo = topicoRepo;
    }

    @GetMapping
    public List<Topico> listar() {
        return topicoRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Topico> crear(@RequestBody @Valid TopicoRequest req,
                                        @AuthenticationPrincipal Usuario autor) {
        Topico t = new Topico();
        t.setTitulo(req.getTitulo());
        t.setMensaje(req.getMensaje());
        t.setCurso(req.getCurso());
        t.setAutor(autor);

        Topico guardado = topicoRepo.save(t);
        return ResponseEntity.created(URI.create("/topicos/" + guardado.getId())).body(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!topicoRepo.existsById(id)) return ResponseEntity.notFound().build();
        topicoRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
