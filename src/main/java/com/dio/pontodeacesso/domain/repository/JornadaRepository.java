package com.dio.pontodeacesso.domain.repository;


import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository<EntidadeParaRepository, TipoChavePrimariaDaEntidade> => Extende os métodos de Crud para esta Classe de Repository
@Repository
public interface JornadaRepository extends JpaRepository<JornadaTrabalho, Long> {

}
