package com.dio.pontodeacesso.application.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class NivelAcessoModel {

    private long id_nivelAcesso;
    private String descricao;
    private List<LocalidadeModel> localidades;
    private List<UsuarioModel> usuarios;
}
