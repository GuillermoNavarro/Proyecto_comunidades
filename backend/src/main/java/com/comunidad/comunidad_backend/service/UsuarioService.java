package com.comunidad.comunidad_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import com.comunidad.comunidad_backend.repository.UsuarioRepository;
import com.comunidad.comunidad_backend.entity.Usuario;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findByComunidadId(Long idComunidad) {
        return usuarioRepository.findByComunidadId(idComunidad);
    }
    
    public boolean deleteUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if(usuario != null){
            usuario.setEstado(false);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }   
}
