package com.Perfulandia.ApiComentarios.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USUARIO")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;


    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;
}