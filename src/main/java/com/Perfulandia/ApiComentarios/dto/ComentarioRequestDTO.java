package com.Perfulandia.ApiComentarios.dto;

import lombok.Data;

@Data
public class ComentarioRequestDTO {
    private String descripcion;
    private int calificacion;
    private int pedido; // ID del pedido
}