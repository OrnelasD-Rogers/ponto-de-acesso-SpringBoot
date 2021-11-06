package com.dio.pontodeacesso.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NivelAcessoModel {

    private long id_nivelAcesso;
    private String descricao;
    private List<LocalidadeModel> localidades;
    private List<UsuarioModel> usuarios;
}
