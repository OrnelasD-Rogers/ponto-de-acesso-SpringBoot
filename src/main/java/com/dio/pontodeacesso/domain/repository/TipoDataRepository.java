package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.model.TipoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDataRepository extends JpaRepository<TipoData, Long> {

    List<TipoData> findByDescricao(String descricao);
}
