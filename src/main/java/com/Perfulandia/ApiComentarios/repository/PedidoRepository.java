package com.Perfulandia.ApiComentarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Perfulandia.ApiComentarios.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
