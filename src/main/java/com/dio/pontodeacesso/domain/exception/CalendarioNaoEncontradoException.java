package com.dio.pontodeacesso.domain.exception;

public class CalendarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public CalendarioNaoEncontradoException(String message) {
        super(message);
    }

    public CalendarioNaoEncontradoException(Long id) {
        this(String.format("Não foi encotrado um Calendário com o id %d", id));
    }

}
