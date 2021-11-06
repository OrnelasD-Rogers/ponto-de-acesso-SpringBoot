package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.NivelDeAcessoNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import com.dio.pontodeacesso.domain.repository.NivelAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NivelAcessoService {

    private final NivelAcessoRepository nivelAcessoRepository;


    @Transactional
    public NivelAcesso save(NivelAcesso nivelAcesso) {
        return  nivelAcessoRepository.save(nivelAcesso);
    }

    public List<NivelAcesso> findAll() {
        return  nivelAcessoRepository.findAll();
    }

    public NivelAcesso findById(Long id) {
        return nivelAcessoRepository.findById(id).orElseThrow(
                () -> new NivelDeAcessoNaoEncontradoException(id)
        );
    }

    @Transactional
    public void delete(long id_nivelAcesso) {
        try {
            nivelAcessoRepository.deleteById(id_nivelAcesso);
            nivelAcessoRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new NivelDeAcessoNaoEncontradoException(id_nivelAcesso);
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("O Nivel de Acesso com id %d está em uso e não pode ser excluido", id_nivelAcesso));
        }
    }
}
