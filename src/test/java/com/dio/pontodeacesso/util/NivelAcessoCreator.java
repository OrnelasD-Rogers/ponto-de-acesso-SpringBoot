package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.model.NivelAcesso;

import java.util.List;

public class NivelAcessoCreator {

    public static NivelAcesso createValidNivelAcesso(){
        return NivelAcesso.builder()
                .descricao("Descricao Teste").build();
    }

    public static NivelAcesso createSavedNivelAcesso(){
        return NivelAcesso.builder()
                .id_nivelAcesso(1L)
                .descricao("Descricao Teste")
                .build();
    }

    public static NivelAcessoModel createValidNivelAcessoModel(){
        return NivelAcessoModel.builder()
                .id_nivelAcesso(1L)
                .descricao("Descricao Teste")
                .localidades(List.of(LocalidadeCreator.createLocalidadeModel()))
                .usuarios(List.of(UsuarioCreator.returnValidUsuarioModel()))
                .build();
    }

    public static NivelAcessoInputModel createValidNivelAcessoInput() {
        return NivelAcessoInputModel.builder()
                .descricao("Descricao Teste")
                .build();
    }

    public static NivelAcessoInputModel createInvalidNivelAcessoInput() {
        return NivelAcessoInputModel.builder()
                .build();
    }
}
