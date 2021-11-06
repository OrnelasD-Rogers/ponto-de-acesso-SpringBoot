package com.dio.pontodeacesso.domain.exception;

import com.dio.pontodeacesso.domain.model.Movimentacao;

public class MovimentacaoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public MovimentacaoNaoEncontradaException(String message) {
        super(message);
    }

    public MovimentacaoNaoEncontradaException(Movimentacao.MovimentacaoId id) {
        this(String.format("Não existe uma movimentação com o id (%d, %d)", id.getId_movimentacao(), id.getId_usuario()));
    }
}
