package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.CategoriaUsuarioNaoEncontrado;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.domain.repository.CategoriaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaUsuarioService {

    private final CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Transactional
    public CategoriaUsuario save(CategoriaUsuario categoriaUsuario) {
        return categoriaUsuarioRepository.save(categoriaUsuario);
    }

    public CategoriaUsuario findById(Long id_cat_usuario) {
        return categoriaUsuarioRepository.findById(id_cat_usuario).orElseThrow(
                () -> new CategoriaUsuarioNaoEncontrado(id_cat_usuario)
        );
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoriaUsuarioRepository.deleteById(id);
            categoriaUsuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CategoriaUsuarioNaoEncontrado(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("A Categoria de Usuario com o código %d não pode ser removida pois está em uso", id));
        }
    }

    public List<CategoriaUsuario> findAll() {
        return categoriaUsuarioRepository.findAll();
    }
}
