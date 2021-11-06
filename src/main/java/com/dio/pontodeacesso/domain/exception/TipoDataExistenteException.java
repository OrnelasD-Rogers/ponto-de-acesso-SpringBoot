package com.dio.pontodeacesso.domain.exception;

public class TipoDataExistenteException extends EntidadeExistenteException {

    public TipoDataExistenteException(String message) {
        super(String.format("A descricao '%s' já existe no banco de dados", message));
    }

}
