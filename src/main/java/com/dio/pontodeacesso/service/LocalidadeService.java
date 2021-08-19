package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.Calendario;
import com.dio.pontodeacesso.model.Localidade;
import com.dio.pontodeacesso.repository.LocalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadeService {
    LocalidadeRepository localidadeRepository;

    @Autowired
    public LocalidadeService(LocalidadeRepository localidadeRepository) {
        this.localidadeRepository = localidadeRepository;
    }

    public Localidade save(Localidade localidade) {
        return  localidadeRepository.save(localidade);
    }

    public List<Localidade> findAll() {
        return localidadeRepository.findAll();
    }

    public Optional<Localidade> findById(Long id) {
        return localidadeRepository.findById(id);
    }

    public void delete(Long id) {
        localidadeRepository.deleteById(id);
    }
}
