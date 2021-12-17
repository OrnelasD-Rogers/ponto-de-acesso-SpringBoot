package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.TipoDataNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.repository.TipoDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TipoDataService {

    private final TipoDataRepository tipoDataRepository;

    public List<TipoData> getTipoData() {
        return tipoDataRepository.findAll();
    }

    @Transactional
    public TipoData save(TipoData tipoData) {
        return tipoDataRepository.save(tipoData);
    }

    public TipoData getTipoDataById(Long idTipoData) {
        return tipoDataRepository.findById(idTipoData).orElseThrow(
                () -> new TipoDataNaoEncontradoException(idTipoData)
        );
    }

    @Transactional
    public TipoData updateTipoData(Long id) {
        TipoData tipoDataAtual = getTipoDataById(id);
        return save(tipoDataAtual);
    }

    @Transactional
    public void deleteTipoData(Long id) {
        try {
            tipoDataRepository.deleteById(id);
            tipoDataRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new TipoDataNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("O Tipo de Data com código %d não pode ser removido pois está em uso", id));
        }
    }
}
