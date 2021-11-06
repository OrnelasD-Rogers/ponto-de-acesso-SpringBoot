package com.dio.pontodeacesso.domain.exception;

import com.dio.pontodeacesso.domain.model.BancoHoras;

public class BancoHorasNaoEncontrado extends EntidadeNaoEncontradaException {


    public BancoHorasNaoEncontrado(String message) {
        super(message);
    }

    public BancoHorasNaoEncontrado(BancoHoras.BancoHorasId id){
        this(String.format("O banco com o id (%d, %d, %d) não foi encontrado",
                id.getId_bancoHoras(),
                id.getId_movimentacao(),
                id.getId_usuario()));
    }
}
