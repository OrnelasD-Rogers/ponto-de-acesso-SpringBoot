package com.dio.pontodeacesso.application.model;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
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
