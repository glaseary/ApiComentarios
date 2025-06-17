package com.Perfulandia.ApiComentarios.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.*;
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

    public ComentarioResponseDTO crearComentario(ComentarioRequestDTO request) {
        Pedido pedido = pedidoRepository.findById(request.getPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + request.getPedido()));

        Comentario comentario = new Comentario();
        comentario.setDescripcion(request.getDescripcion());
        comentario.setCalificacion(request.getCalificacion());
        comentario.setPedido(pedido);

        return toResponseDTO(comentarioRepository.save(comentario));
    }

    public ComentarioResponseDTO actualizarComentario(Integer id, ComentarioRequestDTO request) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con ID: " + id));

        Pedido pedido = pedidoRepository.findById(request.getPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + request.getPedido()));

        comentarioExistente.setDescripcion(request.getDescripcion());
        comentarioExistente.setCalificacion(request.getCalificacion());
        comentarioExistente.setPedido(pedido);

        return toResponseDTO(comentarioRepository.save(comentarioExistente));
    }

    public void eliminarComentario(Integer id) {
        if (!comentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }

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

            UsuarioInfoDTO usuarioDTO = new UsuarioInfoDTO();
            usuarioDTO.setIdUsuario(comentario.getPedido().getUsuario().getIdUsuario());
            usuarioDTO.setNombreUsuario(comentario.getPedido().getUsuario().getNombreUsuario());
            dto.setUsuario(usuarioDTO);

            ProductoInfoDTO productoDTO = new ProductoInfoDTO();
            productoDTO.setIdProducto(comentario.getPedido().getProducto().getIdProducto());
            productoDTO.setNombre(comentario.getPedido().getProducto().getNombre());
            dto.setProducto(productoDTO);

            dto.setPedido(pedidoDTO);
        }

        return dto;
    }
}