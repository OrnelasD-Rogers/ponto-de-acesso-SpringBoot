package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.BancoHorasModel;
import com.dio.pontodeacesso.application.model.input.BancoHorasInputModel;
import com.dio.pontodeacesso.domain.exception.MovimentacaoNaoEncontradaException;
import com.dio.pontodeacesso.domain.exception.UsuarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.model.Usuario;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class BancoHorasCreator {

    private final static Long ID_BANCO_HORAS = 1L;
    private final static Long ID_USUARIO = 1L;
    private final static Long ID_MOVIMENTACAO= 1L;

    public static BancoHoras.BancoHorasId createIdBancoHoras(Long idBancoHoras, Long idMov, Long idUsu){
        return BancoHoras.BancoHorasId.builder()
                .id_bancoHoras(idBancoHoras)
                .id_movimentacao(idMov)
                .id_usuario(idUsu)
                .build();
    }

    public static BancoHoras createValidBancoHoras() {
        return BancoHoras.builder()
                .idBancoHoras(createIdBancoHoras(ID_BANCO_HORAS,ID_USUARIO,ID_MOVIMENTACAO))
                .dataTrabalhada(OffsetDateTime.now())
                .quantidadeHoras(new BigDecimal(6))
                .saldoHoras(new BigDecimal(2))
                .movimentacao(MovimentacaoCreator.createValidMovimentacao())
                .build();
    }


    public static BancoHorasModel createValidBancoHorasModel() {
        return BancoHorasModel.builder()
                .idBancoHoras(new BancoHorasModel.BancoHorasId(ID_BANCO_HORAS, ID_USUARIO, ID_MOVIMENTACAO))
                .dataTrabalhada(OffsetDateTime.now())
                .quantidadeHoras(new BigDecimal(10))
                .saldoHoras(new BigDecimal(10))                
                .build();
    }

    public static BancoHoras createSavedBancoHoras() {
        return BancoHoras.builder()
                .idBancoHoras(createIdBancoHoras(ID_BANCO_HORAS,ID_USUARIO,ID_MOVIMENTACAO))
                .dataTrabalhada(OffsetDateTime.now())
                .quantidadeHoras(new BigDecimal(6))
                .saldoHoras(new BigDecimal(2))
                .movimentacao(MovimentacaoCreator.createValidMovimentacao())
                .build();
    }

    public static BancoHorasInputModel createValidBancoHorasInputModel() {
        return BancoHorasInputModel.builder()
                .idBancoHoras(new BancoHorasInputModel.BancoHorasInputId(ID_BANCO_HORAS,ID_USUARIO,ID_MOVIMENTACAO))
                .dataTrabalhada(OffsetDateTime.now())
                .quantidadeHoras(new BigDecimal(10))
                .saldoHoras(new BigDecimal(10))
                .build();
    }

    public static BancoHorasInputModel createInvalidDataTrabalhadaInBancoHorasInput() {
        return BancoHorasInputModel.builder()
                .idBancoHoras(new BancoHorasInputModel.BancoHorasInputId(ID_BANCO_HORAS,ID_USUARIO,ID_MOVIMENTACAO))
                .quantidadeHoras(new BigDecimal(10))
                .saldoHoras(new BigDecimal(10))
                .build();
    }
}
