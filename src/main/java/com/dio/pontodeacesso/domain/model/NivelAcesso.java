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
public class NivelAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_nivelAcesso;

    private String descricao;

    @Column(insertable = false, updatable = false)
    @OneToMany(mappedBy = "nivelAcesso")
    private List<Localidade> localidade;

    @OneToMany(mappedBy = "nivelAcesso")
    private List<Usuario> usuarios;

}
