package com.dio.pontodeacesso.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
@IdClass(MovimentacaoId.class)
public class Movimentacao {
    private static final long serialVersionUID = 5978541324789007386L;

    @Id
    @GeneratedValue
    private long id_Movimento;
    @Id
    @GeneratedValue
    private long id_Usuario;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private BigDecimal periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Ocorrencia")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ocorrencia ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Calendario")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Calendario calendario;
}
