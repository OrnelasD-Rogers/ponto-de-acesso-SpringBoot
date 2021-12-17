package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;

public class OcorrenciaCreator {

    public static Ocorrencia createValidOcorrencia(){
        return Ocorrencia.builder()
                .nome("Nova ocorrencia teste")
                .descricao("Nova desc teste").build();
    }

    public static OcorrenciaModel createValidOcorrenciaModel(){
        return OcorrenciaModel.builder()
                .id_Ocorrencia(10L)
                .nome("Nova ocorrencia teste")
                .descricao("Nova desc teste")
                .build();
    }

    public  static OcorrenciaInputModel createValidOcorrenciaInputModel(){
        return OcorrenciaInputModel.builder()
                .nome("Nova ocorrencia teste")
                .descricao("Nova desc teste")
                .build();
    }

    public  static OcorrenciaInputModel createInvalidDescInOcorrenciaInputModel(){
        return OcorrenciaInputModel.builder()
                .nome("Nova ocorrencia teste")
                .build();
    }

    public  static OcorrenciaInputModel createInvalidNomeInOcorrenciaInputModel(){
        return OcorrenciaInputModel.builder()
                .descricao("Nova desc teste")
                .build();
    }
}
