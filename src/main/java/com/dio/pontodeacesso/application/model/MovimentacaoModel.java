package com.dio.pontodeacesso.application.model;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter
public class MovimentacaoModel {

    private MovimentacaoModelId movimentacaoId;
    private OffsetDateTime dataEntrada;
    private OffsetDateTime dataSaida;
    private BigDecimal periodo;
    private UsuarioModel usuario;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class MovimentacaoModelId implements Serializable {

        private long id_movimentacao;

        private long id_usuario;
    }
}
