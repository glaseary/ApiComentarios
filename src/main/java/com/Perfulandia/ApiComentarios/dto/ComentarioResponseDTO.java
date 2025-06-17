package com.Perfulandia.ApiComentarios.dto;
import lombok.Data;

@Data
public class ComentarioResponseDTO {
    private Integer idComentario;
    private String descripcion;
    private int calificacion;
    private PedidoDTO pedido;
    private ProductoInfoDTO producto;
    private UsuarioInfoDTO usuario;
}