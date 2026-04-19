package com.comunidad.comunidad_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.comunidad.comunidad_backend.repository.UsuarioRepository;
import com.comunidad.comunidad_backend.dto.CambioPass;
import com.comunidad.comunidad_backend.entity.Usuario;
import com.comunidad.comunidad_backend.enus.Rol;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        if(usuarioRepository.findByDni(usuario.getDni()).isPresent()){
            throw new IllegalArgumentException("Ya existe un usuario con el DNI:" + usuario.getDni());
        }
        
        usuario.setRol(Rol.USER);
        usuario.setEstado(true);
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setCambiarPass(true);
        usuarioRepository.save(usuario);

        emailService.enviarMailBienvenida(usuario.getEmail(), newPassword);

        return usuario;
    }

    public List<Usuario> findByComunidadId(Long idComunidad) {
        return usuarioRepository.findByComunidadId(idComunidad);
    }

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario findByEmail(String email){
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    
    public boolean deleteUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if(usuario != null){
            usuario.setEstado(false);
            usuario.setCoeficiente(0.0);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
    
    public Usuario login(String email, String password){
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        
        if(usuario == null){
            return null;
        }
        if(!usuario.getEstado()){
            return null;
        }
        if(!passwordEncoder.matches(password, usuario.getPassword())) {
            return null;
        } 

        return usuario;            
    }

    private void actualizarComunes(Usuario actual, Usuario nuevo){
        if(nuevo.getNombre() != null){
            actual.setNombre(nuevo.getNombre());
        }
        if(nuevo.getApellidos() != null){
            actual.setApellidos(nuevo.getApellidos());
        }
        if(nuevo.getTelefono() != null){
            actual.setTelefono(nuevo.getTelefono());
        }
    }

    public Usuario modificarUsuario(Long id, Usuario usuarioNuevo){
        Usuario usuarioActual = usuarioRepository.findById(id).orElse(null);
        if(usuarioActual == null){
            return null;
        }
        actualizarComunes(usuarioActual, usuarioNuevo);
        usuarioRepository.save(usuarioActual);
        return usuarioActual;
    }

    public Usuario modificarUsuarioAdmin(Long id, Usuario usuarioNuevo){
        Usuario usuarioActual = usuarioRepository.findById(id).orElse(null);
        if (usuarioActual == null) {
            return null;
        }
        actualizarComunes(usuarioActual, usuarioNuevo);
        if(usuarioNuevo.getDni() != null){
            usuarioActual.setDni(usuarioNuevo.getDni());
        }
        if(usuarioNuevo.getPuerta() != null){
            usuarioActual.setPuerta(usuarioNuevo.getPuerta());
        }
        if (usuarioNuevo.getEmail() != null) {
            usuarioActual.setEmail(usuarioNuevo.getEmail());
        }
        if(usuarioNuevo.getCoeficiente() != null){
            usuarioActual.setCoeficiente(usuarioNuevo.getCoeficiente());
        }
        if(usuarioNuevo.getRol() != null){
            if(usuarioNuevo.getRol() == Rol.SUPER_ADMIN){
                usuarioActual.setRol(Rol.USER);
            }else{
                usuarioActual.setRol(usuarioNuevo.getRol());
            }            
        }
        if(usuarioNuevo.getEstado() != null){
            usuarioActual.setEstado(usuarioNuevo.getEstado());
        }
        usuarioRepository.save(usuarioActual);
        return usuarioActual;
    }

    public Usuario cambioPassword(Long id, CambioPass cambioPass){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if(!passwordEncoder.matches(cambioPass.getOldPassword(), usuario.getPassword())){
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(cambioPass.getNewPassword()));
        usuario.setCambiarPass(false);

        return usuarioRepository.save(usuario);
    }

    public void cambioPassAdmin(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setCambiarPass(true);
        usuarioRepository.save(usuario);

        emailService.enviarMailContraseña(usuario.getEmail(), newPassword);
    }

}
