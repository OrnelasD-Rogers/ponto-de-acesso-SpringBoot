package com.dio.pontodeacesso.domain.exception;

import com.dio.pontodeacesso.domain.model.Localidade;

public class LocalidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public LocalidadeNaoEncontradaException(String message) {
        super(message);
    }

    public LocalidadeNaoEncontradaException(Localidade.LocalidadeId id) {
        this(String.format("A localidade com o id (%d, %d) não foi encontrada", id.getId(), id.getId_nivelAcesso()));
    }
}
