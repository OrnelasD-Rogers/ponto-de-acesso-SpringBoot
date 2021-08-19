package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.Usuario;
import com.dio.pontodeacesso.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> findAll() {
        return  usuarioRepository.findAll();
    }
}
