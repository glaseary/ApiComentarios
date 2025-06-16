package com.Perfulandia.ApiComentarios.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // <-- Con la corrección de nombre
    private Integer idUsuario;


    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;
}