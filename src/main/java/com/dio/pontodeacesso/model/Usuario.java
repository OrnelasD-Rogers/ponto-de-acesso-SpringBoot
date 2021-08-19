package com.dio.pontodeacesso.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.envers.Audited;

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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Cat_Usuario")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CategoriaUsuario categoriaUsuario;
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Empresa")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_NivelAcesso")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NivelAcesso nivelAcesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Jornada_Trabalho")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private JornadaTrabalho jornadaTrabalho;

    private BigDecimal tolerancia;
    private LocalDateTime inicioJornada;
    private LocalDateTime finalJornada;

}
