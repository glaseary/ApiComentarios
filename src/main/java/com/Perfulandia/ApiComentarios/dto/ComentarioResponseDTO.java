package com.Perfulandia.ApiComentarios.dto;
import lombok.Data;

@Data
public class ComentarioResponseDTO {
    private Integer idComentario;
    private String descripcion;
    private int calificacion;
    private UsuarioInfoDTO usuario;
    private ProductoInfoDTO producto;
}