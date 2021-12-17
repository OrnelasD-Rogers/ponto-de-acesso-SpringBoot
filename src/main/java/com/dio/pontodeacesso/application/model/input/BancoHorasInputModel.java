package com.dio.pontodeacesso.application.model.input;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class BancoHorasInputModel {

    @Valid
    @NotNull
    private BancoHorasInputId idBancoHoras;

    @NotNull
    private OffsetDateTime dataTrabalhada;

    @NotNull
    @PositiveOrZero
    private BigDecimal quantidadeHoras;

    @NotNull
    @PositiveOrZero
    private BigDecimal saldoHoras;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class BancoHorasInputId implements Serializable {

        @NotNull
        private Long id_bancoHoras;

        @NotNull
        private Long id_movimentacao;

        @NotNull
        private Long id_usuario;
    }
}
