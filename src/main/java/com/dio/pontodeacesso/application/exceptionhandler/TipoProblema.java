package com.dio.pontodeacesso.application.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblema {
    PROPRIEDADE_JA_EXISTENTE("Propriedade já existente"),
    RECURSO_NAO_ENCONTRADO("Recurso não encotnrado"),
    ENTIDADE_EM_USO("Entidade em uso"),
    ERRO_NEGOCIO("Erro de negócio"),
    DADOS_INVALIDOS("Dados inválidos"),
    PARAMETRO_INVALIDO("Parâmetro Inválido"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem Incompreensível"),
    PROPRIEDADE_DESCONHECIDA("Propriedade Desconhecida"),
    PROPRIEDADE_INEXISTENTE("Propriedade Inexistente");



    private String titulo;

    TipoProblema(String title) {
        this.titulo = title;
    }

}
