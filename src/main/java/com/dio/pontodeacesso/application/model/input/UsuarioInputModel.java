package com.dio.pontodeacesso.application.model.input;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UsuarioInputModel {

    @Valid
    private CategoriaUsuarioIdInputModel categoriaUsuario;

    @Valid
    private EmpresaIdInputModel empresa;

    @Valid
    private NivelAcessoIdIputModel nivelAcesso;

    @Valid
    private JornadaTrabalhoIdInputModel jornadaTrabalho;

    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal tolerancia;
    @NotNull
    private OffsetDateTime inicioJornada;
    @NotNull
    private OffsetDateTime finalJornada;




}
