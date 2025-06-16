package com.Perfulandia.ApiComentarios.dto;
import lombok.Data;

@Data
public class ComentarioRequestDTO {
    private String descripcion;
    private int calificacion;
    private Integer usuarioId;
    private Integer productoId;
}