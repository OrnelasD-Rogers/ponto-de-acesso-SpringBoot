package com.dio.pontodeacesso.application.model.input;


import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @NotNull
    private OcorrenciaIdInputModel ocorrencia;
    @Valid
    @NotNull
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
