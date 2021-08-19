package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.model.BancoHoras;
import com.dio.pontodeacesso.model.BancoHorasId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository<EntidadeParaRepository, TipoChavePrimariaDaEntidade> => Extende os métodos de Crud para esta Classe de Repository
@Repository
public interface BancoHorasRepository extends JpaRepository<BancoHoras, BancoHorasId> {
}
