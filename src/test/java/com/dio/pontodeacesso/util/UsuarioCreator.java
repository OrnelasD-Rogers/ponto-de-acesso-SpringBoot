package com.dio.pontodeacesso.util;

import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.*;
import com.dio.pontodeacesso.domain.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class UsuarioCreator {

    public static Usuario createValidUsuario() {
        return Usuario.builder()
                .categoriaUsuario(CategoriaUsuario.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(Empresa.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcesso.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalho.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static Usuario createInvalidUsuario_FK_CategoriaUsuario() {
        return Usuario.builder()
                .categoriaUsuario(CategoriaUsuario.builder().id_cat_usuario(1000L).build())
                .nome("Novo nome")
                .empresa(Empresa.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcesso.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalho.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static Usuario createInvalidUsuario_FK_Empresa() {
        return Usuario.builder()
                .categoriaUsuario(CategoriaUsuario.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(Empresa.builder().id_empresa(10000L).build())
                .nivelAcesso(NivelAcesso.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalho.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static Usuario createInvalidUsuario_FK_NivelAcesso() {
        return Usuario.builder()
                .categoriaUsuario(CategoriaUsuario.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(Empresa.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcesso.builder().id_nivelAcesso(10000L).build())
                .jornadaTrabalho(JornadaTrabalho.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static Usuario createInvalidUsuario_FK_JornadaTrabalho() {
        return Usuario.builder()
                .categoriaUsuario(CategoriaUsuario.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(Empresa.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcesso.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalho.builder().id_jornada_trabalho(1000L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createValidUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidUsuarioNomeInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidFkEmpresaForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.parse("2021-11-07T01:28:16.211Z"))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidFkNivelUsuarioForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidFkJornadaTrabalhoForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidToleranciaForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidIniciojornadaForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidFinaljornadaForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .nome("Novo nome")
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .build();
    }

    public static UsuarioInputModel createInvalidCategoriaUsuarioForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .nome("Novo nome")
                .categoriaUsuario(new CategoriaUsuarioIdInputModel())
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidEmpresaForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .nome("Novo nome")
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .empresa(new EmpresaIdInputModel())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidNivelAcessoForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .nome("Novo nome")
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(new NivelAcessoIdIputModel())
                .jornadaTrabalho(JornadaTrabalhoIdInputModel.builder().id_jornada_trabalho(1L).build())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioInputModel createInvalidJornadaTrabalhoForUsuarioInputModel() {
        return UsuarioInputModel.builder()
                .nome("Novo nome")
                .categoriaUsuario(CategoriaUsuarioIdInputModel.builder().id_cat_usuario(1L).build())
                .empresa(EmpresaIdInputModel.builder().id_empresa(1L).build())
                .nivelAcesso(NivelAcessoIdIputModel.builder().id_nivelAcesso(1L).build())
                .jornadaTrabalho(new JornadaTrabalhoIdInputModel())
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }

    public static UsuarioModel returnValidUsuarioModel() {
        return UsuarioModel.builder()
                .id_usuario(10L)
                .nome("Novo nome")
                .tolerancia(new BigDecimal(10))
                .inicioJornada(OffsetDateTime.of(2021, 8, 20, 15, 30, 0, 0, ZoneOffset.UTC))
                .finalJornada(OffsetDateTime.now())
                .build();
    }
}
