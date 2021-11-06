package com.dio.pontodeacesso.application.model.input;


import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter @Setter
public class MovimentacaoInputModel {

    @Valid
    private MovimentacaoInputId movimentacaoId;
    @NotNull
    private OffsetDateTime dataEntrada;
    @NotNull
    private OffsetDateTime dataSaida;
    @NotNull
    private BigDecimal periodo;
    @Valid
    private OcorrenciaIdInputModel ocorrencia;
    @Valid
    private CalendarioIdInputModel calendario;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class MovimentacaoInputId implements Serializable {

        @NotNull
        private Long id_movimentacao;

        @NotNull
        private Long id_usuario;
    }
}
