package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.JornadaTrabalhoNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import com.dio.pontodeacesso.domain.repository.JornadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JornadaService {

    private final JornadaRepository jornadaRepository;

    @Transactional
    public JornadaTrabalho save(JornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }


    public List<JornadaTrabalho> findAll(){
        return  jornadaRepository.findAll();
    }


    public JornadaTrabalho findById(Long idJornada){
        return jornadaRepository.findById(idJornada).orElseThrow(
                ()-> new JornadaTrabalhoNaoEncontradaException(idJornada)
        );
    }

    @Transactional
    public void delete(Long idJornada){
        try {
            jornadaRepository.deleteById(idJornada);
            jornadaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new JornadaTrabalhoNaoEncontradaException(idJornada);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("A Jornada de Trabalho com código %d não pode ser removida pois está em uso", idJornada));
        }
    }

    public JornadaTrabalho update(JornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }
}
