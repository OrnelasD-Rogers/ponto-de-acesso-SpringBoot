package com.dio.pontodeacesso.domain.exception;

public class NivelDeAcessoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public NivelDeAcessoNaoEncontradoException(String message) {
        super(message);
    }

    public NivelDeAcessoNaoEncontradoException(Long id) {
        this(String.format("O nível de acesso de id %d não foi encontrado", id));
    }
}
