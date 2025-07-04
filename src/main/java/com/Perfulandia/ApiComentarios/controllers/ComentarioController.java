package com.Perfulandia.ApiComentarios.controllers;

import com.Perfulandia.ApiComentarios.dto.ComentarioRequestDTO;
import com.Perfulandia.ApiComentarios.dto.ComentarioResponseDTO;
import com.Perfulandia.ApiComentarios.services.ComentarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Importar el método estático para crear links más fácil
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // --- Endpoints CRUD (Sin cambios) ---

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

    // --- Endpoints HATEOAS (Modificados) ---

    /**
     * Devuelve un solo comentario con links detallados, imitando el ejemplo de "Provincia" individual.
     * GET /api/comentarios/{id}/hateoas
     */
    @GetMapping("/hateoas/{id}")
    public ResponseEntity<Object> getComentarioHATEOAS(@PathVariable Integer id) {
        try {
            ComentarioResponseDTO comentario = comentarioService.listarPorId(id);
            String gatewayUrl = "http://localhost:8888/api/proxy/comentarios";
            // Suponiendo una URL para pedidos
            String pedidosGatewayUrl = "http://localhost:8888/api/proxy/pedidos"; 

            // Limpiamos links anteriores si los hubiera
            comentario.removeLinks(); 

            // Añadir enlaces HATEOAS imitando tu ejemplo
            comentario.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
            comentario.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-comentarios"));
            
            // Link al recurso relacionado (equivalente a "comunas" en tu ejemplo)
            if (comentario.getPedido() != null) {
                comentario.add(Link.of(pedidosGatewayUrl + "/" + comentario.getPedido().getIdPedido()).withRel("pedido"));
            }

            comentario.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));
            comentario.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

            return ResponseEntity.ok(comentario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Devuelve la lista de comentarios, donde cada uno tiene sus links "self" y "crear",
     * imitando el ejemplo de la lista de "Provincias".
     * GET /api/comentarios/hateoas
     */
    @GetMapping("/hateoas")
    public ResponseEntity<List<ComentarioResponseDTO>> listarHATEOAS() {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarTodos();
        String gatewayUrl = "http://localhost:8888/api/proxy/comentarios";

        for (ComentarioResponseDTO dto : comentarios) {
            // Limpiamos links anteriores si los hubiera
            dto.removeLinks();
            
            // Link a sí mismo (self)
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdComentario()).withSelfRel());
            
            // Link para crear un nuevo recurso
            dto.add(Link.of(gatewayUrl).withRel("crear-comentario").withType("POST"));
        }
        return ResponseEntity.ok(comentarios);
    }
}