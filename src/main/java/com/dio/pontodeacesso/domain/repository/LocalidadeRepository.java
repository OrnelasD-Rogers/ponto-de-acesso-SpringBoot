package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.model.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadeRepository extends JpaRepository<Localidade, Localidade.LocalidadeId> {
}
