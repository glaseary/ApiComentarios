package com.Perfulandia.ApiComentarios.services;

import org.springframework.stereotype.Service;

import com.Perfulandia.ApiComentarios.dto.UsuarioInfoDTO;
import com.Perfulandia.ApiComentarios.models.Usuario;

@Service
public class UsuarioService {
    public UsuarioInfoDTO toInfoDTO(Usuario usuario) {
        UsuarioInfoDTO dto = new UsuarioInfoDTO(); 
        dto.setIdUsuario(usuario.getIdUsuario()); 
        dto.setNombreUsuario(usuario.getNombreUsuario()); 
        return dto; 
    }


}