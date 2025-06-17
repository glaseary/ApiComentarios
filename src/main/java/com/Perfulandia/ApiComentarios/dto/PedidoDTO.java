package com.Perfulandia.ApiComentarios.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PedidoDTO {
    private int idPedido;
    private LocalDate fechaPedido;
    private String estado;
    private int totalNeto;
}
