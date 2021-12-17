package com.dio.pontodeacesso.application.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(content = JsonInclude.Include.NON_NULL, value = JsonInclude.Include.NON_EMPTY)
@Getter
@Builder
@ToString
public class ProblemaDetails {

    private Integer status;
    //Extensão
    private OffsetDateTime timestamp;
    private String titulo;
    private String detalhes;
    //Extensão
    private String mensagemUsuario;
    private List<Objeto> objetos;


    @Getter
    @Builder
    public static class Objeto {
        private String nome;
        private String mensagemUsuario;
    }
}
