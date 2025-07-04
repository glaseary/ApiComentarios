package com.Perfulandia.ApiComentarios.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.ComentarioRequestDTO;
import com.Perfulandia.ApiComentarios.dto.ComentarioResponseDTO;
import com.Perfulandia.ApiComentarios.dto.PedidoDTO;
import com.Perfulandia.ApiComentarios.models.Comentario;
import com.Perfulandia.ApiComentarios.models.Pedido;
import com.Perfulandia.ApiComentarios.repository.ComentarioRepository;
import com.Perfulandia.ApiComentarios.repository.PedidoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired private ComentarioRepository comentarioRepository;
    @Autowired private PedidoRepository pedidoRepository;

    public List<ComentarioResponseDTO> listarTodos() {
        return comentarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ComentarioResponseDTO listarPorId(Integer id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con ID: " + id));
        return toResponseDTO(comentario);
    }

    public ComentarioResponseDTO crearComentario(ComentarioRequestDTO request) {
        Pedido pedido = pedidoRepository.findById(request.getPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + request.getPedido()));

        Comentario comentario = new Comentario();
        comentario.setDescripcion(request.getDescripcion());
        comentario.setCalificacion(request.getCalificacion());
        comentario.setPedido(pedido);
        
        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        return toResponseDTO(comentarioGuardado);
    }

    public ComentarioResponseDTO actualizarComentario(Integer id, ComentarioRequestDTO request) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con ID: " + id));

        Pedido pedido = pedidoRepository.findById(request.getPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + request.getPedido()));

        comentarioExistente.setDescripcion(request.getDescripcion());
        comentarioExistente.setCalificacion(request.getCalificacion());
        comentarioExistente.setPedido(pedido);

        Comentario comentarioActualizado = comentarioRepository.save(comentarioExistente);
        return toResponseDTO(comentarioActualizado);
    }

    public void eliminarComentario(Integer id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }

    // --- MÉTODO PRIVADO DE CONVERSIÓN SIMPLIFICADO ---
    private ComentarioResponseDTO toResponseDTO(Comentario comentario) {
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setIdComentario(comentario.getIdComentario());
        dto.setDescripcion(comentario.getDescripcion());
        dto.setCalificacion(comentario.getCalificacion());

        if (comentario.getPedido() != null) {
            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setIdPedido(comentario.getPedido().getIdPedido());
            pedidoDTO.setFechaPedido(comentario.getPedido().getFechaPedido());
            pedidoDTO.setEstado(comentario.getPedido().getEstado());
            pedidoDTO.setTotalNeto(comentario.getPedido().getTotalNeto());
            
            dto.setPedido(pedidoDTO);
        }
        // Ya no se mapea el producto ni el usuario
        return dto;
    }
}