package com.dio.pontodeacesso.domain.exception;

public class TipoDataNaoEncontradoException extends EntidadeNaoEncontradaException{
    public TipoDataNaoEncontradoException(String message) {
        super(message);
    }

    public TipoDataNaoEncontradoException(Long id) {
        this(String.format("Não foi encontrado um Tipo Data com o id %d", id));
    }
}
