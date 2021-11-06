package com.dio.pontodeacesso.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OcorrenciaModel {

    private long id_Ocorrencia;
    private String nome;
    private String descricao;
    private List<MovimentacaoModel> movimentacao;
}
