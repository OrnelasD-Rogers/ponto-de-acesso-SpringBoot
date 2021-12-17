package com.dio.pontodeacesso.application.model;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class JornadaTrabalhoModel {

    private Long id_jornada_trabalho;
    private String descricao;
    private List<UsuarioModel> usuario;
}
