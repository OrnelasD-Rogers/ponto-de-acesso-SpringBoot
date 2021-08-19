package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.CategoriaUsuario;
import com.dio.pontodeacesso.repository.CategoriaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaUsuarioService {
    CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Autowired
    public CategoriaUsuarioService(CategoriaUsuarioRepository categoriaUsuarioRepository) {
        this.categoriaUsuarioRepository = categoriaUsuarioRepository;
    }

    public CategoriaUsuario save(CategoriaUsuario categoriaUsuario) {
        return categoriaUsuarioRepository.save(categoriaUsuario);
    }

    public Optional<CategoriaUsuario> findById(Long id_cat_usuario) {
        return categoriaUsuarioRepository.findById(id_cat_usuario);
    }

    public void delete(Long id) {
        categoriaUsuarioRepository.deleteById(id);
    }

    public List<CategoriaUsuario> findAll() {
        return categoriaUsuarioRepository.findAll();
    }
}
