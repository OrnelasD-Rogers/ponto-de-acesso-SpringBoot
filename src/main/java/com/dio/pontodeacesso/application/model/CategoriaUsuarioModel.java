package com.dio.pontodeacesso.application.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CategoriaUsuarioModel {

    private Long id_cat_usuario;

    private String descricao;

    private List<UsuarioModel> usuario;
}
