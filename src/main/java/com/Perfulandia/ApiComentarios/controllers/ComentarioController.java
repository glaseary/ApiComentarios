package com.Perfulandia.ApiComentarios.controllers;

import com.Perfulandia.ApiComentarios.dto.ComentarioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Perfulandia.ApiComentarios.dto.ComentarioRequestDTO;
import com.Perfulandia.ApiComentarios.dto.ComentarioResponseDTO;
import com.Perfulandia.ApiComentarios.services.ComentarioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;


    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(comentarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getComentarioPorId(@PathVariable Integer id) {
        try {
            ComentarioResponseDTO comentario = comentarioService.listarPorId(id);
            return ResponseEntity.ok(comentario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody ComentarioRequestDTO request) {
        try {
            ComentarioResponseDTO comentarioCreado = comentarioService.crearComentario(request);
            return new ResponseEntity<>(comentarioCreado, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody ComentarioRequestDTO request) {
        try {
            return ResponseEntity.ok(comentarioService.actualizarComentario(id, request));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            comentarioService.eliminarComentario(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/hateoas")
    public ResponseEntity<Object> getComentarioHATEOAS(@PathVariable Integer id) {
        try {
            ComentarioResponseDTO comentario = comentarioService.listarPorId(id);
            String gatewayUrl = "http://localhost:8888/api/proxy/comentarios";

            // Añadir enlaces HATEOAS específicos para este comentario
            comentario.add(Link.of(gatewayUrl + "/" + id).withSelfRel().withType("GET"));
            comentario.add(Link.of(gatewayUrl + "/" + id).withRel("editar-comentario").withType("PUT"));
            comentario.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar-comentario").withType("DELETE"));
            comentario.add(Link.of(gatewayUrl).withRel("todos-los-comentarios").withType("GET"));

            return ResponseEntity.ok(comentario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/hateoas")
    public List<ComentarioResponseDTO> listarHATEOAS() {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarTodos();
        String gatewayUrl = "http://localhost:8888/api/proxy/comentarios";

        for (ComentarioResponseDTO dto : comentarios) {
            dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-comentario").withType("POST"));
            dto.add(Link.of(gatewayUrl).withRel("editar-comentario").withType("PUT"));
            dto.add(Link.of(gatewayUrl).withRel("eliminar-comentario").withType("DELETE"));
        }
        return comentarios;
    }
}
