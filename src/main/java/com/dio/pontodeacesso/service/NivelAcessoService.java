package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.NivelAcesso;
import com.dio.pontodeacesso.repository.NivelAcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NivelAcessoService {
    NivelAcessoRepository nivelAcessoRepository;

    @Autowired
    public NivelAcessoService(NivelAcessoRepository nivelAcessoRepository) {
        this.nivelAcessoRepository = nivelAcessoRepository;
    }

    public NivelAcesso save(NivelAcesso nivelAcesso) {
        return  nivelAcessoRepository.save(nivelAcesso);
    }

    public List<NivelAcesso> findAll() {
        return  nivelAcessoRepository.findAll();
    }

    public Optional<NivelAcesso> findById(Long id) {
        return nivelAcessoRepository.findById(id);
    }

    public void delete(long id_nivelAcesso) {
        nivelAcessoRepository.deleteById(id_nivelAcesso);
    }
}
