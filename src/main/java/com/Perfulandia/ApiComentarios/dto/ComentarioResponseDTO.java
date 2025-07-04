package com.Perfulandia.ApiComentarios.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ComentarioResponseDTO extends RepresentationModel<ComentarioResponseDTO> {
    private Integer idComentario;
    private String descripcion;
    private int calificacion;
    private PedidoDTO pedido; // Solo el pedido, ya no hay producto ni usuario
}