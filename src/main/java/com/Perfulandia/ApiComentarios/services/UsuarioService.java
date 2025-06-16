package com.Perfulandia.ApiComentarios.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.UsuarioInfoDTO;
import com.Perfulandia.ApiComentarios.models.Usuario;
import com.Perfulandia.ApiComentarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**

    Busca un usuario en la tabla de réplica local por su ID.
    Este es su propósito principal en este microservicio. */ 
    public Usuario findUsuarioById(Integer id) { 
        return usuarioRepository.findById(id) 
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id)); }

    public UsuarioInfoDTO toInfoDTO(Usuario usuario) {
        UsuarioInfoDTO dto = new UsuarioInfoDTO(); 
        dto.setIdUsuario(usuario.getIdUsuario()); 
        dto.setNombreUsuario(usuario.getNombreUsuario()); 
        return dto; 
    }


}