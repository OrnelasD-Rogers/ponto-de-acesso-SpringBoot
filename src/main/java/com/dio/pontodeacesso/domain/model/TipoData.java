package com.dio.pontodeacesso.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class TipoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipoData;

    private String descricao;
}
