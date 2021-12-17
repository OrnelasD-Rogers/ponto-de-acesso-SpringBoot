package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.application.model.input.CalendarioIdInputModel;
import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaIdInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.model.Ocorrencia;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MovimentacaoCreator {

    private static final Long VALID_MOV_ID = 1L;

    private static final Long VALID_USU_ID = 1L;

    public static Movimentacao.MovimentacaoId movimentacaoId(Long idMov, Long idUsu) {
        return Movimentacao.MovimentacaoId.builder()
                .id_movimentacao(idMov)
                .id_usuario(idUsu).build();
    }

    public static MovimentacaoModel.MovimentacaoModelId movimentacaoModelId(Long idMov, Long idUsu) {
        return MovimentacaoModel.MovimentacaoModelId.builder()
                .id_movimentacao(idMov)
                .id_usuario(idUsu).build();
    }

    public static MovimentacaoInputModel.MovimentacaoInputId movimentacaoInputId(Long idMov, Long idUsu) {
        return MovimentacaoInputModel.MovimentacaoInputId.builder()
                .id_movimentacao(idMov)
                .id_usuario(idUsu).build();
    }

    public static Movimentacao createValidMovimentacao() {
        return Movimentacao.builder()
                .movimentacaoId(Movimentacao.MovimentacaoId.builder().id_movimentacao(VALID_MOV_ID).id_usuario(VALID_USU_ID).build())
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(Ocorrencia.builder().id_ocorrencia(1L).build())
                .calendario(Calendario.builder().id_calendario(1L).build())
                .usuario(UsuarioCreator.createValidUsuario())
                .build();
    }

    public static MovimentacaoModel createValidMovimentacaoModel(){
        return MovimentacaoModel.builder()
                .movimentacaoId(movimentacaoModelId(VALID_MOV_ID,VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .usuario(UsuarioCreator.returnValidUsuarioModel())
                .build();
    }


    public static MovimentacaoInputModel createValidMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createInvalidMovimentacaoIdInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(new MovimentacaoInputModel.MovimentacaoInputId(null, 1L))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createInvalidUsuarioIdInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(new MovimentacaoInputModel.MovimentacaoInputId(1L, null))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createInvalidDataEntradaInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createInvalidDataSaidaInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createInvalidPeriodoInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createNullId_OcorrenciaInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(null))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createNullOcorrenciaInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .calendario(new CalendarioIdInputModel(1L))
                .build();
    }



    public static MovimentacaoInputModel createNullCalendarioInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .build();
    }

    public static MovimentacaoInputModel createNullId_CalendarioInMovimentacaoInput() {
        return MovimentacaoInputModel.builder()
                .movimentacaoId(movimentacaoInputId(VALID_MOV_ID, VALID_USU_ID))
                .dataEntrada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .dataSaida(OffsetDateTime.now())
                .periodo(new BigDecimal(10))
                .ocorrencia(new OcorrenciaIdInputModel(1L))
                .calendario(new CalendarioIdInputModel(null))
                .build();
    }


}
