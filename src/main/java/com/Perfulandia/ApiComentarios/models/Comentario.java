// src/main/java/com/Perfulandia/ApiComentarios/models/Comentario.java
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

    @Column(nullable = false) // No necesitas length para un tipo int
    private int calificacion;

    // ----- CORRECCIÓN AQUÍ -----
    // La relación la define Comentario, porque tiene la clave foránea.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEDIDO_id_pedido", nullable = false)
    private Pedido pedido;
}