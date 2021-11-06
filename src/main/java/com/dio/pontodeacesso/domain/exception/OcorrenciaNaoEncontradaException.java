package com.dio.pontodeacesso.domain.exception;

public class OcorrenciaNaoEncontradaException extends EntidadeNaoEncontradaException{

    public OcorrenciaNaoEncontradaException(String message) {
        super(message);
    }

    public OcorrenciaNaoEncontradaException(Long id) {
        this(String.format("Não foi encontrado uma Ocorrencia com o id %d", id));
    }
}
