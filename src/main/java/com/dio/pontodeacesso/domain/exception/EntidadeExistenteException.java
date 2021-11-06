package com.dio.pontodeacesso.domain.exception;

public abstract class EntidadeExistenteException extends NegocioException{

    public EntidadeExistenteException(String message) {
        super(message);
    }
}
