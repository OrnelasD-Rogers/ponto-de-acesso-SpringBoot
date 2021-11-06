package com.dio.pontodeacesso.domain.exception;

public class CategoriaUsuarioNaoEncontrado extends EntidadeNaoEncontradaException {
    public CategoriaUsuarioNaoEncontrado(String message) {
        super(message);
    }

    public CategoriaUsuarioNaoEncontrado(Long id_cat_usuario) {
        this(String.format("A Categoria de usuário com id %d não foi encontrada", id_cat_usuario));
    }
}
