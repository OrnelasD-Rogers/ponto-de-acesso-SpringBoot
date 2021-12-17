package com.dio.pontodeacesso.application.model;


import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class BancoHorasModel {

    private BancoHorasId idBancoHoras;

    private OffsetDateTime dataTrabalhada;
    private BigDecimal quantidadeHoras;
    private BigDecimal saldoHoras;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class BancoHorasId implements Serializable {

        private long id_bancoHoras;

        private long id_movimentacao;

        private long id_usuario;
    }
}
