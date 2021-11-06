package com.dio.pontodeacesso.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Calendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_calendario;

    private String descricao;

    private OffsetDateTime dataEspecial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoData")
    private TipoData tipoData;

    @OneToMany(mappedBy = "calendario", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacao;

}
