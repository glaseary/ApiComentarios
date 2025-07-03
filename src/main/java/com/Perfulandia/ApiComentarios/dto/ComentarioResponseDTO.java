package com.Perfulandia.ApiComentarios.dto;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ComentarioResponseDTO extends RepresentationModel<ComentarioResponseDTO> {
    private Integer idComentario;
    private String descripcion;
    private int calificacion;
    private PedidoDTO pedido;
    private ProductoInfoDTO producto;
    private UsuarioInfoDTO usuario;
}