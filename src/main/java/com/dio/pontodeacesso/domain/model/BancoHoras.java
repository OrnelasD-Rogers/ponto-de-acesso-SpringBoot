package com.dio.pontodeacesso.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class BancoHoras {

    @EmbeddedId
    private BancoHorasId idBancoHoras;

    @MapsId("idBancoHoras")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_movimentacao"),
            @JoinColumn(name = "id_usuario")
    })
    private Movimentacao movimentacao;

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

        private Long id_bancoHoras;

        private Long id_movimentacao;

        private Long id_usuario;


    }
}
