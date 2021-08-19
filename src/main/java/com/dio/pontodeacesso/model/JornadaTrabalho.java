package com.dio.pontodeacesso.model;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;


//Notacoes lombok. Servem para deixar as Entidades mais limpas
@Getter //Define os Getters para as propriedades
@Setter //Define os Setter para as propriedades
@AllArgsConstructor //Cria um construtor com todos os argumentos
@NoArgsConstructor //Cria um construtor sem argumentos
@EqualsAndHashCode //Sobrescreve os Metodos Equals e HashCode
@Builder //Serve para transferir objetos entre classes e fazer testes
@Entity
//@Audited //Audita a Entidade
public class JornadaTrabalho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Jornada_Trabalho;
    //@Audited //Pode ser usado para auditar atributos separados de uma Entidade
    private String descricao;

    @OneToMany(mappedBy = "jornadaTrabalho", cascade = CascadeType.ALL)
    private List<Usuario> usuario;


}
