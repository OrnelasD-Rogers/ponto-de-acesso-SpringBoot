package com.dio.pontodeacesso.application.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class TipoDataModel {

    private Long id_tipoData;

    private String descricao;
}
