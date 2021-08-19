package com.dio.pontodeacesso.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
@IdClass(BancoHorasId.class)
public class BancoHoras implements Serializable {
    private static final long serialVersionUID = 5999236902534007386L;

    @Id
    @GeneratedValue
    private long idBancoHoras;
    @Id
    @GeneratedValue
    private long idMovimento;
    @Id
    @GeneratedValue
    private long idUsuario;

    private LocalDateTime dataTrabalhada;
    private BigDecimal quantidadeHoras;
    private BigDecimal saldoHoras;
}
