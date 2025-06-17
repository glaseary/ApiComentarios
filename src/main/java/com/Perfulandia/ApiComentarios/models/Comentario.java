package com.Perfulandia.ApiComentarios.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "COMENTARIO")
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false, length = 11)
    private int calificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEDIDO_id_pedido", nullable = false)
    private Pedido pedido;

}