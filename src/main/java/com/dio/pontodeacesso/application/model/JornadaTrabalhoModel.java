package com.dio.pontodeacesso.application.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JornadaTrabalhoModel {

    private Long id_jornada_trabalho;
    private String descricao;
    private List<UsuarioModel> usuario;
}
