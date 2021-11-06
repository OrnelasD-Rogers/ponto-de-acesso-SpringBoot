package com.dio.pontodeacesso.application.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter
public class UsuarioModel {

    private Long id_usuario;
    private String nome;
    private BigDecimal tolerancia;
    private OffsetDateTime inicioJornada;
    private OffsetDateTime finalJornada;

}
