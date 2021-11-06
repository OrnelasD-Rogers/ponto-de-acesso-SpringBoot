package com.dio.pontodeacesso.domain.service;


import com.dio.pontodeacesso.domain.exception.CalendarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.repository.CalendarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarioService {

    private final CalendarioRepository calendarioRepository;
    private final TipoDataService tipoDataService;

    @Transactional
    public Calendario save(Calendario calendario) {
        tipoDataService.getTipoDataById(calendario.getTipoData().getId_tipoData());
        return calendarioRepository.save(calendario);
    }

    public List<Calendario> findAllCalendario() {
        return calendarioRepository.findAll();
    }

    public Calendario findCalendarioById(Long idCalendario) {
        return calendarioRepository.findById(idCalendario).orElseThrow(
                () -> new CalendarioNaoEncontradoException(idCalendario)
        );
    }

    @Transactional
    public Calendario update(Calendario calendario) {
        return calendarioRepository.save(calendario);
    }

    @Transactional
    public void deleteCalendario(Long id) {
        try {
            calendarioRepository.deleteById(id);
            calendarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CalendarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("O calendario com o código %d não pode ser removido pois está em uso", id));
        }
    }
}
