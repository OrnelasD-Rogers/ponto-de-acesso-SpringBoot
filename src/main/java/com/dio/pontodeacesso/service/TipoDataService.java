package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.TipoData;
import com.dio.pontodeacesso.repository.TipoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDataService {
    TipoDataRepository tipoDataRepository;

    @Autowired
    public TipoDataService(TipoDataRepository tipoDataRepository) {
        this.tipoDataRepository = tipoDataRepository;
    }

    public List<TipoData> getTipoData() {
        return tipoDataRepository.findAll();
    }

    public TipoData save(TipoData tipoData) {
        return tipoDataRepository.save(tipoData);
    }

    public Optional<TipoData> getTipoDataById(Long idTipoData) {
        return tipoDataRepository.findById(idTipoData);
    }

    public TipoData updateTipoData(TipoData tipoData) {
        return tipoDataRepository.save(tipoData);
    }

    public void deleteTipoData(Long id) {
        tipoDataRepository.deleteById(id);
    }
}
