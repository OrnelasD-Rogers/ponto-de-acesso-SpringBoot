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
public class CategoriaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cat_usuario;

    private String descricao;

    @OneToMany(mappedBy = "categoriaUsuario", cascade = CascadeType.ALL)
    private List<Usuario> usuario;
}
