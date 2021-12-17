package com.dio.pontodeacesso.domain.exception;

public class EmpresaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public EmpresaNaoEncontradaException(String message) {
        super(message);
    }

    public EmpresaNaoEncontradaException(Long id_empresa) {
        this(String.format("A empresa com o id %d não foi encotnrada", id_empresa));
    }
}
