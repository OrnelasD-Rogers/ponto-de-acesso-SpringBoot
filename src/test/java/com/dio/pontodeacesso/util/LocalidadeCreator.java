package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
import com.dio.pontodeacesso.domain.exception.NivelDeAcessoNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.model.NivelAcesso;

public class LocalidadeCreator {

    private static Long ID_NIVEL_ACESSO  = 1L;
    private static Long ID_LOCALIDADE = 1L;

    public static LocalidadeModel createLocalidadeModel(){
        return LocalidadeModel.builder()
                .localidadeId(LocalidadeModel.LocalidadeIdOutput.builder()
                        .id(1L)
                        .id_NivelAcesso(1L)
                        .build())
                .descricao("Descricao Teste")
                .build();
    }

    public static Localidade.LocalidadeId createLocalidadeId(Long idLocalidade, Long idnivelAcesso){
        return Localidade.LocalidadeId.builder()
                .id(idLocalidade)
                .id_nivelAcesso(idnivelAcesso).build();
    }

    public static Localidade createValidLocalidade(){
        return Localidade.builder()
                .localidadeId(createLocalidadeId(ID_LOCALIDADE,ID_NIVEL_ACESSO))
                .descricao("Nova desc")
                .nivelAcesso(NivelAcessoCreator.createValidNivelAcesso())
                .build();
    }

    public static LocalidadeInputModel createValidLocalidadeInput(){
        return LocalidadeInputModel.builder()
                .localidadeId(new LocalidadeInputModel.LocalidadeId(ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .descricao("Desc teste")
                .build();
    }

    public static LocalidadeInputModel createInvalidIdInLocalidadeInput(){
        return LocalidadeInputModel.builder()
                .localidadeId(null)
                .descricao("Desc teste")
                .build();
    }

    public static LocalidadeInputModel createInvalidDescricaoInLocalidadeInput(){
        return LocalidadeInputModel.builder()
                .localidadeId(new LocalidadeInputModel.LocalidadeId(ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .build();
    }

    public static LocalidadeInputModel createInvalidIdInLocalidadeInputId(){
        return LocalidadeInputModel.builder()
                .localidadeId(new LocalidadeInputModel.LocalidadeId(null, ID_NIVEL_ACESSO))
                .descricao("Desc teste")
                .build();
    }

    public static LocalidadeInputModel createInvalidNivelAcessoIdInLocalidadeInput(){
        return LocalidadeInputModel.builder()
                .localidadeId(new LocalidadeInputModel.LocalidadeId(ID_LOCALIDADE, null))
                .descricao("Desc teste")
                .build();
    }

}
