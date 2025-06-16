package com.Perfulandia.ApiComentarios.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.ProductoInfoDTO;
import com.Perfulandia.ApiComentarios.models.Producto;
import com.Perfulandia.ApiComentarios.repository.ProductoRepository;

@Service
public class ProductoService {

@Autowired
private ProductoRepository productoRepository;

    public Producto findProductoById(Integer id) {
        return productoRepository.findById(id) 
        .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id)); 
        }

    public ProductoInfoDTO toInfoDTO(Producto producto) {
        ProductoInfoDTO dto = new ProductoInfoDTO(); 
        dto.setIdProducto(producto.getIdProducto()); 
        dto.setNombre(producto.getNombre()); 
        return dto; 
        }
}