package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
import com.dio.pontodeacesso.domain.model.Empresa;

import java.util.List;

public class EmpresaCreator {

    private static final Long ID_EMPRESA = 1L;

    public static Empresa createValidEmpresa(){
        return Empresa.builder()
                .descricao("Nova desc")
                .bairro("novo bairro")
                .cidade("nova cidade")
                .cnpj("novo cnpj")
                .estado("Novo estado")
                .telefone("5555555555")
                .endereco("novo endereco")
                .build();
    }

    public static Empresa createSavedEmpresa(){
        return Empresa.builder()
                .id_empresa(ID_EMPRESA)
                .descricao("Nova desc")
                .bairro("novo bairro")
                .cidade("nova cidade")
                .cnpj("novo cnpj")
                .estado("Novo estado")
                .telefone("5555555555")
                .endereco("novo endereco")
                .build();
    }

    public static EmpresaModel createValidEmpresaModel(){
        return EmpresaModel.builder()
                .id_Empresa(ID_EMPRESA)
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .usuario(List.of(UsuarioCreator.returnValidUsuarioModel()))
                .build();
    }

    public static EmpresaInputModel createValidEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    /////////

    public static EmpresaInputModel createInvalidDescricaoInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidCnpjInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidEnderecoInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidBairroInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidCidadeInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .estado("Estado Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidEstadoInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .telefone("99999999999")
                .build();
    }

    public static EmpresaInputModel createInvalidTelefoneInEmpresaInputModel() {
        return EmpresaInputModel.builder()
                .descricao("Empresa Teste")
                .cnpj("555555555")
                .endereco("Endereco Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .build();
    }
}
