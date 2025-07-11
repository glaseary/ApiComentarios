// src/main/java/com/Perfulandia/ApiComentarios/models/Pedido.java
package com.Perfulandia.ApiComentarios.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "PEDIDO")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "total_neto", nullable = false)
    private Integer totalNeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTO_id_producto", nullable = false)
    private Producto producto;

    // ----- CORRECCIÓN AQUÍ -----
    // Esta es la relación inversa. Comentario es el dueño.
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Comentario comentario;
}