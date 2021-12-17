package com.dio.pontodeacesso.application.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class OcorrenciaModel {

    private long id_Ocorrencia;
    private String nome;
    private String descricao;
    private List<MovimentacaoModel> movimentacao;
}
