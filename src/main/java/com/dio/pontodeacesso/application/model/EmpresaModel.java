package com.dio.pontodeacesso.application.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class EmpresaModel {

    private Long id_Empresa;
    private String descricao;
    private String cnpj;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
    private List<UsuarioModel> usuario;
}
