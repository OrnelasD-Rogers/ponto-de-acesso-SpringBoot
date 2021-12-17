package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;

import java.util.List;

public class JornadaTrabalhoCreator {

    private static final Long ID_JORNADA = 1L;

    public static JornadaTrabalho createValidJornadaTrabalho(){
        return JornadaTrabalho.builder()
                .id_jornada_trabalho(ID_JORNADA)
                .descricao("Nova Desc")
                .build();
    }

    public static JornadaTrabalho createValidJornadaTrabalhoToBeSaved(){
        return JornadaTrabalho.builder()
                .descricao("Nova Desc")
                .build();
    }

    public static JornadaTrabalhoModel createValidJornadaTrabalhoModel(){
        return JornadaTrabalhoModel.builder()
                .id_jornada_trabalho(ID_JORNADA)
                .descricao("Desc Teste")
                .usuario(List.of(UsuarioCreator.returnValidUsuarioModel()))
                .build();
    }

    public static JornadaTrabalhoInputModel createValidJornadaTrabalhoInputModel(){
        return JornadaTrabalhoInputModel.builder()
                .descricao("Desc Teste")
                .build();
    }

    public static JornadaTrabalhoInputModel createInvalidDescricaoInJornadaTrabalhoInputModel(){
        return JornadaTrabalhoInputModel.builder()
                .build();
    }
}
