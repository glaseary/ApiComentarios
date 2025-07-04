package com.Perfulandia.ApiComentarios.repository;

import com.Perfulandia.ApiComentarios.models.Comentario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    /**
     * Al usar @EntityGraph, le decimos a JPA: "Cuando llames a este método,
     * asegúrate de traer también el 'pedido' asociado, en una sola consulta".
     */
    @Override
    @EntityGraph(attributePaths = {"pedido"}) // <-- Simplificado para traer solo el pedido
    List<Comentario> findAll();

    @Override
    @EntityGraph(attributePaths = {"pedido"}) // <-- Simplificado para traer solo el pedido
    Optional<Comentario> findById(Integer id);
}