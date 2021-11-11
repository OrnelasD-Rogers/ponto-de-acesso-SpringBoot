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
public class Movimentacao {

    @EmbeddedId
    private MovimentacaoId movimentacaoId;

    private OffsetDateTime dataEntrada;
    private OffsetDateTime dataSaida;
    private BigDecimal periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ocorrencia")
    private Ocorrencia ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_calendario")
    private Calendario calendario;

    @MapsId("id_usuario")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class MovimentacaoId implements Serializable {

        private Long id_movimentacao;

        private Long id_usuario;


    }
}
