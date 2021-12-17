package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.model.TipoData;

public class TipoDataCreator {

    public static TipoData createTipoData(){
        return TipoData.builder()
                .descricao("Novo tipo de data teste")
                .build();
    }

    public static TipoData createSavedTipoData(){
        return TipoData.builder()
                .id_tipoData(1L)
                .descricao("Novo tipo de data teste")
                .build();
    }

    public static TipoDataModel createTipoDataModel(){
        return TipoDataModel.builder()
                .id_tipoData(1L)
                .descricao("Novo tipo de data teste")
                .build();
    }

    public static TipoDataInputModel createTipoDataInputModel() {
        return TipoDataInputModel.builder()
                .descricao("Novo tipo de data teste")
                .build();
    }

    public static TipoDataInputModel createInvalidTipoDataInputModel() {
        return TipoDataInputModel.builder()
                .build();
    }
}
