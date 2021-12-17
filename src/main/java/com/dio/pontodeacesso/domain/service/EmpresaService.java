package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EmpresaNaoEncontradaException;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.Empresa;
import com.dio.pontodeacesso.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa findById(Long id_empresa) {
        return  empresaRepository.findById(id_empresa).orElseThrow(
                ()-> new EmpresaNaoEncontradaException(id_empresa)
        );
    }

    @Transactional
    public void delete(Long id) {
        try {
            empresaRepository.deleteById(id);
            empresaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EmpresaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("A Empresa com o id %d está em uso e não pode ser excluida", id));
        }
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }
}
