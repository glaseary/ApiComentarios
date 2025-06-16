package com.Perfulandia.ApiComentarios.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Lista TODOS los comentarios.
     * GET /api/comentarios
     */
    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(comentarioService.listarTodos());
    }

    /**
     * Crea un nuevo comentario.
     * POST /api/comentarios
     */
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody ComentarioRequestDTO request) {
        try {
            ComentarioResponseDTO comentarioCreado = comentarioService.crearComentario(request);
            return new ResponseEntity<>(comentarioCreado, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualiza un comentario existente.
     * PUT /api/comentarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody ComentarioRequestDTO request) {
        try {
            return ResponseEntity.ok(comentarioService.actualizarComentario(id, request));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Elimina un comentario por su ID.
     * DELETE /api/comentarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            comentarioService.eliminarComentario(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- ENDPOINTS DE BÚSQUEDA ESPECÍFICA ---
    
    /**
     * Obtiene todos los comentarios de un producto específico.
     * GET /api/comentarios/producto/{productoId}
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerPorProducto(@PathVariable Integer productoId) {
        try {
            return ResponseEntity.ok(comentarioService.buscarPorProducto(productoId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Obtiene todos los comentarios de un usuario específico.
     * GET /api/comentarios/usuario/{usuarioId}
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.ok(comentarioService.buscarPorUsuario(usuarioId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
