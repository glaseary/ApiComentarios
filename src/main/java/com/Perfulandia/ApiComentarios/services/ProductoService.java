package com.Perfulandia.ApiComentarios.services;

import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.ProductoInfoDTO;
import com.Perfulandia.ApiComentarios.models.Producto;

@Service
public class ProductoService {

    public ProductoInfoDTO toInfoDTO(Producto producto) {
        ProductoInfoDTO dto = new ProductoInfoDTO(); 
        dto.setIdProducto(producto.getIdProducto()); 
        dto.setNombre(producto.getNombre()); 
        return dto; 
        }
}