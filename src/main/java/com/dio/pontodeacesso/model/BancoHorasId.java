package com.dio.pontodeacesso.model;

import lombok.*;


import java.io.Serializable;

@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
public class BancoHorasId implements Serializable {
    private static final long serialVersionUID = -637018809489152388L;

    private long idBancoHoras;

    private long idMovimento;

    private long idUsuario;
}
