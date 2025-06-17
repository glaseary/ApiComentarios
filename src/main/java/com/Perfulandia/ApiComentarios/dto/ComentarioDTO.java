package com.Perfulandia.ApiComentarios.dto;

import lombok.Data;

@Data
public class ComentarioDTO {
    private int idComentario;
    private String descripcion;
    private int Calificacion;
    private int Pedido;

}
