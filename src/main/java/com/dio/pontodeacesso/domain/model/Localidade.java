package com.dio.pontodeacesso.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Localidade {

    @EmbeddedId
    private LocalidadeId localidadeId;

    @MapsId("id_nivelAcesso")
    @ManyToOne
    @JoinColumn(name = "id_nivelAcesso", nullable = false)
    private NivelAcesso nivelAcesso;

    private String descricao;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class LocalidadeId implements Serializable {

        private long id;

        private long id_nivelAcesso;

    }
}
