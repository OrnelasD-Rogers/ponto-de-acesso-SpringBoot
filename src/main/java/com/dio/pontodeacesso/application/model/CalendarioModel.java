package com.dio.pontodeacesso.application.model;


import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CalendarioModel {

    private Long id_calendario;
    private String descricao;
    private OffsetDateTime dataEspecial;
    private List<MovimentacaoModel> movimentacao;
}
