package com.dio.pontodeacesso.domain.repository;

import com.dio.pontodeacesso.domain.model.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
}
