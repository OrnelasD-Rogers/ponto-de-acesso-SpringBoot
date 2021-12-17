package com.dio.pontodeacesso.application.model;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CategoriaUsuarioModel {

    private Long id_cat_usuario;

    private String descricao;

    private List<UsuarioModel> usuario;
}
