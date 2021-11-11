package com.dio.pontodeacesso.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ocorrencia;

    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacao;

}
