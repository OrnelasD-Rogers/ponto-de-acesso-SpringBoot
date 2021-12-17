package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;

import java.util.List;

public class CategoriaUsuarioCreator {

    private final static Long ID_CAT_USU = 1L;

    public static CategoriaUsuario createValidCategoriaUsuario(){
        return CategoriaUsuario.builder()
                .descricao("Nova desc")
                .build();
    }

    public static CategoriaUsuario createSavedCategoriaUsuario(){
        return CategoriaUsuario.builder()
                .id_cat_usuario(ID_CAT_USU)
                .descricao("Nova desc")
                .build();
    }

    public static CategoriaUsuarioModel createValidCategoriaUsuarioModel(){
        return CategoriaUsuarioModel.builder()
                .id_cat_usuario(ID_CAT_USU)
                .descricao("Desc Teste")
                .usuario(List.of(UsuarioCreator.returnValidUsuarioModel()))
                .build();
    }

    public static CategoriaUsuarioInputModel createValidCategoriaUsuarioInputModel(){
        return CategoriaUsuarioInputModel.builder()
                .descricao("Desc Teste")
                .build();
    }

    public static CategoriaUsuarioInputModel createInvalidDescricaoInCategoriaUsuarioInputModel() {
        return CategoriaUsuarioInputModel.builder()
                .build();
    }
}
