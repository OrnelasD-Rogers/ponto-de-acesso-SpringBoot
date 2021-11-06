package com.dio.pontodeacesso.application.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter
public class UsuarioInputModel {

    @Valid
    private CategoriaUsuarioIdInputModel categoriaUsuario;

    @NotBlank
    private String nome;

    @Valid
    private EmpresaIdInputModel empresa;

    @Valid
    private NivelAcessoIdIputModel nivelAcesso;

    @Valid
    private JornadaTrabalhoIdInputModel jornadaTrabalho;

    @NotNull
    private BigDecimal tolerancia;
    @NotNull
    private OffsetDateTime inicioJornada;
    @NotNull
    private OffsetDateTime finalJornada;




}
