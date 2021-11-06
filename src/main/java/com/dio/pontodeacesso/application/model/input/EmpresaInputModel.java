package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class EmpresaInputModel {

    @NotBlank
    private String descricao;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String endereco;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String telefone;
}
