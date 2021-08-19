package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.Empresa;
import com.dio.pontodeacesso.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Optional<Empresa> findById(Long id_empresa) {
        return  empresaRepository.findById(id_empresa);
    }

    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }
}
