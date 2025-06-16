package com.Perfulandia.ApiComentarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Perfulandia.ApiComentarios.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}