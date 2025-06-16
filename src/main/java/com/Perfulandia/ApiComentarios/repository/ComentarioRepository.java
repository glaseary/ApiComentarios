package com.Perfulandia.ApiComentarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Perfulandia.ApiComentarios.models.Comentario;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    // Métodos para buscar comentarios por producto o por usuario
    List<Comentario> findByProductoIdProducto(Integer idProducto);
    List<Comentario> findByUsuarioIdUsuario(Integer idUsuario);


}