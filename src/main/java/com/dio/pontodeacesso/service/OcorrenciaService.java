package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.Ocorrencia;
import com.dio.pontodeacesso.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OcorrenciaService {

    OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    public Ocorrencia save(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Optional<Ocorrencia> findById(long id_ocorrencia) {
        return ocorrenciaRepository.findById(id_ocorrencia);
    }

    public void delete(long id_ocorrencia) {
        ocorrenciaRepository.deleteById(id_ocorrencia);
    }

    public List<Ocorrencia> findAll() {
        return ocorrenciaRepository.findAll();
    }
}
