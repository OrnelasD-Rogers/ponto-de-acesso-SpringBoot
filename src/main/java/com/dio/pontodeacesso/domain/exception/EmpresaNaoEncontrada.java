package com.dio.pontodeacesso.domain.exception;

public class EmpresaNaoEncontrada extends EntidadeNaoEncontradaException {
    public EmpresaNaoEncontrada(String message) {
        super(message);
    }

    public EmpresaNaoEncontrada(Long id_empresa) {
        this(String.format("A empresa com o id %d não foi encotnrada", id_empresa));
    }
}
