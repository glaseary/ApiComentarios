package com.Perfulandia.ApiComentarios.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.ComentarioRequestDTO;
import com.Perfulandia.ApiComentarios.dto.ComentarioResponseDTO;
import com.Perfulandia.ApiComentarios.dto.ProductoInfoDTO;
import com.Perfulandia.ApiComentarios.dto.UsuarioInfoDTO;
import com.Perfulandia.ApiComentarios.models.Comentario;
import com.Perfulandia.ApiComentarios.models.Producto;
import com.Perfulandia.ApiComentarios.models.Usuario;
import com.Perfulandia.ApiComentarios.repository.ComentarioRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired private ComentarioRepository comentarioRepository;
    @Autowired private UsuarioService usuarioService;
    @Autowired private ProductoService productoService;

    // --- MÉTODO GET (Listar Todos)
    public List<ComentarioResponseDTO> listarTodos() {
        return comentarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODO POST (Crear) ---
    public ComentarioResponseDTO crearComentario(ComentarioRequestDTO request) {
        Usuario usuario = usuarioService.findUsuarioById(request.getUsuarioId());
        Producto producto = productoService.findProductoById(request.getProductoId());

        Comentario comentario = new Comentario();
        comentario.setDescripcion(request.getDescripcion());
        comentario.setCalificacion(request.getCalificacion());
        comentario.setUsuario(usuario);
        comentario.setProducto(producto);

        return toResponseDTO(comentarioRepository.save(comentario));
    }

    // --- MÉTODO PUT (Actualizar)
    public ComentarioResponseDTO actualizarComentario(Integer id, ComentarioRequestDTO request) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con ID: " + id));

        Usuario usuario = usuarioService.findUsuarioById(request.getUsuarioId());
        Producto producto = productoService.findProductoById(request.getProductoId());

        comentarioExistente.setDescripcion(request.getDescripcion());
        comentarioExistente.setCalificacion(request.getCalificacion());
        comentarioExistente.setUsuario(usuario);
        comentarioExistente.setProducto(producto);
        // La fecha no se actualiza

        return toResponseDTO(comentarioRepository.save(comentarioExistente));
    }

    // --- Métodos de Búsqueda ---
    public List<ComentarioResponseDTO> buscarPorProducto(Integer productoId) {
        productoService.findProductoById(productoId);
        return comentarioRepository.findByProductoIdProducto(productoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public List<ComentarioResponseDTO> buscarPorUsuario(Integer usuarioId) {
        usuarioService.findUsuarioById(usuarioId);
        return comentarioRepository.findByUsuarioIdUsuario(usuarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODO DELETE ---
    public void eliminarComentario(Integer id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
    
    // --- Conversor a DTO ---
    private ComentarioResponseDTO toResponseDTO(Comentario comentario) {
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setIdComentario(comentario.getIdComentario());
        dto.setDescripcion(comentario.getDescripcion());
        dto.setCalificacion(comentario.getCalificacion());
        
        UsuarioInfoDTO usuarioInfo = new UsuarioInfoDTO();
        usuarioInfo.setIdUsuario(comentario.getUsuario().getIdUsuario());
        usuarioInfo.setNombreUsuario(comentario.getUsuario().getNombreUsuario());
        dto.setUsuario(usuarioInfo);
        
        ProductoInfoDTO productoInfo = new ProductoInfoDTO();
        productoInfo.setIdProducto(comentario.getProducto().getIdProducto());
        productoInfo.setNombre(comentario.getProducto().getNombre());
        dto.setProducto(productoInfo);
        
        return dto;
    }
}