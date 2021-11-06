package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.OcorrenciaNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;


    @Transactional
    public Ocorrencia save(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    public Ocorrencia findById(long id_ocorrencia) {
        return ocorrenciaRepository.findById(id_ocorrencia).orElseThrow(
                () -> new OcorrenciaNaoEncontradaException(id_ocorrencia)
        );
    }

    @Transactional
    public void delete(long id_ocorrencia) {
        try {
            ocorrenciaRepository.deleteById(id_ocorrencia);
            ocorrenciaRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
                throw new OcorrenciaNaoEncontradaException(id_ocorrencia);
            } catch (DataIntegrityViolationException e){
                throw new EntidadeEmUsoException(
                        String.format("A Ocorrencia com o código %d não pode ser removida pois está em uso", id_ocorrencia));
            }
    }

    public List<Ocorrencia> findAll() {
        return ocorrenciaRepository.findAll();
    }
}
