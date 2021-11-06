package com.dio.pontodeacesso.domain.exception;

public class JornadaTrabalhoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public JornadaTrabalhoNaoEncontradaException(String message) {
        super(message);
    }

    public JornadaTrabalhoNaoEncontradaException(Long idJornada) {
        this(String.format("A entidade Jornada de Trabalho com id %d não foi encontrada", idJornada));
    }
}
