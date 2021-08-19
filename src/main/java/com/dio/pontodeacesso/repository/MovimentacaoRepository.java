package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.model.Movimentacao;
import com.dio.pontodeacesso.model.MovimentacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, MovimentacaoId> {
}
