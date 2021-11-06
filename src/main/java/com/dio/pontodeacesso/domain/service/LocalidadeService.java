package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.LocalidadeNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.repository.LocalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocalidadeService {

    private final LocalidadeRepository localidadeRepository;
    private final NivelAcessoService nivelAcessoService;


    @Transactional
    public Localidade save(Localidade localidade) {
        localidade.setNivelAcesso(nivelAcessoService.findById(localidade.getLocalidadeId().getId_nivelAcesso()));
        return  localidadeRepository.save(localidade);
    }

    public List<Localidade> findAll() {
        return localidadeRepository.findAll();
    }

    public Localidade findById(Localidade.LocalidadeId id) {
        return localidadeRepository.findById(id).orElseThrow(
                () -> new LocalidadeNaoEncontradaException(id)
        );
    }

    @Transactional
    public void delete(Localidade.LocalidadeId id) {
        try {
            localidadeRepository.deleteById(id);
            localidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new LocalidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("A localidade com o id (%d, %d) está em uso",
                            id.getId(), id.getId_nivelAcesso()));
        }
    }
}