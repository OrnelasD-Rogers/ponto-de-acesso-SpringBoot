package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.application.model.input.TipoDataIdInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.model.TipoData;

import java.time.OffsetDateTime;

public class CalendarioCreator {

    private final static Long ID_CALENDARIO = 1L;

    public static Calendario createValidCalendario(){
        return Calendario.builder()
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .tipoData(TipoData.builder().id_tipoData(1L).build())
                .build();
    }

    public static Calendario createInvalidCalendario(){
        return Calendario.builder()
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .tipoData(TipoData.builder().id_tipoData(11111L).build())
                .build();
    }

    public static CalendarioModel createValidCalendarioModel() {
        return CalendarioModel.builder()
                .id_calendario(ID_CALENDARIO)
                .descricao("Desc Teste")
                .dataEspecial(OffsetDateTime.now())
                .build();
    }

    public static Calendario createSavedCalendario() {
        return Calendario.builder()
                .id_calendario(ID_CALENDARIO)
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .tipoData(TipoData.builder().id_tipoData(1L).build())
                .build();
    }

    public static CalendarioInputModel createValidCalendarioInputModel() {
        return CalendarioInputModel.builder()
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .tipoData(new TipoDataIdInputModel(1L))
                .build();
    }

    public static CalendarioInputModel createInvalidDescricaoInCalendarioInputModel() {
        return CalendarioInputModel.builder()
                .dataEspecial(OffsetDateTime.now())
                .tipoData(new TipoDataIdInputModel(1L))
                .build();
    }

    public static CalendarioInputModel createInvalidTipoDataInCalendarioInputModel() {
        return CalendarioInputModel.builder()
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .build();
    }

    public static CalendarioInputModel createInvalidIdTipoDataInCalendarioInputModel() {
        return CalendarioInputModel.builder()
                .descricao("Novo Calendario")
                .dataEspecial(OffsetDateTime.now())
                .tipoData(new TipoDataIdInputModel(null))
                .build();
    }
}
