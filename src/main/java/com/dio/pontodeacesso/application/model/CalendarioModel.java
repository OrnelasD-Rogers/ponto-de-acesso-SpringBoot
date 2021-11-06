package com.dio.pontodeacesso.application.model;


import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter @Setter
public class CalendarioModel {

    private Long id_calendario;
    private String descricao;
    private OffsetDateTime dataEspecial;
    private List<MovimentacaoModel> movimentacao;
}
