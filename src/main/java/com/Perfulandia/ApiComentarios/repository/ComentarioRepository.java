package com.Perfulandia.ApiComentarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Perfulandia.ApiComentarios.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

}